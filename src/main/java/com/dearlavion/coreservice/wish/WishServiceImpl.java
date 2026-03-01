package com.dearlavion.coreservice.wish;

import com.dearlavion.coreservice.common.ai.AiService;
import com.dearlavion.coreservice.kafka.KafkaEventProducer;
import com.dearlavion.coreservice.request.RequestService;
import com.dearlavion.coreservice.wish.search.WishSearchRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestService requestService;

    @Autowired
    private AiService aiService;

    @Autowired
    private Optional<KafkaEventProducer> wishEventProducer;

    @Override
    public Optional<WishDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, WishDTO.class));
    }

    @Override
    public Optional<WishDTO> findByUsername(String username) {
        return repo.findByUsername(username).map(e -> mapper.map(e, WishDTO.class));
    }

    @Override
    public WishDTO create(WishDTO dto) {
        validate(dto);

        // Prevent empty string ID (Mongo will treat it as provided)
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(null);
        }
        Wish entity = mapper.map(dto, Wish.class);

        //to avoid duplicate categories
        entity.setCategories(populateCategories(dto));

        // 🔥 Set createdAt only on creation
        entity.setCreatedAt(new Date());

        // 🔥 Optional: also set updatedAt = createdAt (good practice)
        entity.setUpdatedAt(entity.getCreatedAt());

        entity.setStatus("OPEN");

        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            entity.setGeoPoints(new double[] { dto.getLongitude(), dto.getLatitude() });
        }

        Wish saved = repo.save(entity);
        wishEventProducer.ifPresent(producer -> producer.publishWishCreated(saved));
        return mapper.map(saved, WishDTO.class);
    }

    @Override
    public WishDTO update(String id, WishDTO dto) {
        validate(dto);
        return repo.findById(id).map(existing -> {
            // Map incoming fields into existing entity
            mapper.map(dto, existing);

            // Ensure categories properly copied
            existing.setCategories(dto.getCategories());

            // 🔥 Update timestamp
            existing.setUpdatedAt(new Date());

            Wish saved = repo.save(existing);
            return mapper.map(saved, WishDTO.class);
        }).orElseThrow(() -> new RuntimeException("Wish not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<WishDTO> findAllByUsername(String username) {
        return repo.findAllByUsername(username)
                .stream()
                .map(e -> mapper.map(e, WishDTO.class))
                .toList();
    }

    @Override
    public WishDTO patch(String id, Map<String, Object> updates) throws JsonMappingException {
        Wish wish = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wish not found"));

        // Extract request operations
        Object requestIdObj = updates.remove("requestId");
        String requestId = requestIdObj != null ? requestIdObj.toString() : null;

        Object copilotNameObj = updates.remove("copilotName");
        String copilotName = copilotNameObj != null ? copilotNameObj.toString() : null;

        Object statusObj = updates.remove("status");
        String status = statusObj != null ? statusObj.toString() : null;

        Object removeRequestObj = updates.remove("removeRequest");
        boolean removeRequest = removeRequestObj != null && Boolean.parseBoolean(removeRequestObj.toString());

        Object wishCompleteObj = updates.remove("wishComplete");
        boolean wishComplete = wishCompleteObj != null && Boolean.parseBoolean(wishCompleteObj.toString());

        Object wishReOpenObj = updates.remove("wishReOpen");
        boolean wishReOpen = wishReOpenObj != null && Boolean.parseBoolean(wishReOpenObj.toString());


        // Merge other scalar fields
        objectMapper.updateValue(wish, updates);

        // Initialize list if null
        if (wish.getWishRequestList() == null) {
            wish.setWishRequestList(new ArrayList<>());
        }

        if (removeRequest) {
            // 🔹 Remove the matching WishRequestDTO in WishRequestList[]
            wish.getWishRequestList().removeIf(wr -> requestId.equals(wr.getRequestId()));
        } else if (wishComplete) {
            updateWishToComplete(wish);
        } else if (wishReOpen) {
            reOpenWish(wish);
        }
        else {
            updateWishRequestList(wish, requestId, copilotName, status);
        }

        // Update timestamp
        wish.setUpdatedAt(new Date());
        Wish saved = repo.save(wish);

        return mapper.map(saved, WishDTO.class);
    }

    private void reOpenWish(Wish wish) {
        // Reset the wish status
        wish.setStatus("OPEN");
    }

    private void updateWishToComplete(Wish wish) {
        wish.getWishRequestList().forEach(wr -> {
            String newStatus = "ONGOING".equals(wr.getStatus())
                    ? "COMPLETED"
                    : "REJECTED";

            wr.setStatus(newStatus);

            // 🔥 update Request table also
            Map<String, Object> updates = new HashMap<>();
            updates.put("status", newStatus);

            try {
                this.requestService.patch(wr.getRequestId(), updates);
            } catch (JsonMappingException e) {
                // handle exception, e.g., log
                e.printStackTrace();
            }
        });

        wish.setStatus("COMPLETED");
    }


    private void updateWishRequestList(Wish wish, String requestId, String copilotName, String status) {
        // Find request by requestId (NOT copilot name)
        Optional<WishRequestDTO> wishRequest = wish.getWishRequestList().stream()
                .filter(wr -> requestId != null && requestId.equals(wr.getRequestId()))
                .findFirst();

        if (wishRequest.isEmpty()) {
            WishRequestDTO wr = new WishRequestDTO();
            wr.setRequestId(requestId);
            wr.setCopilotName(copilotName);
            wr.setStatus(status);
            wish.getWishRequestList().add(wr);
        } else {
            // Update wish request status only on existing
            WishRequestDTO wr = wishRequest.get();

            if (status != null) {
                wr.setStatus(status);
            }
            updateWishStatus(wish);
        }

    }

    private void updateWishStatus(Wish wish) {
        Optional<WishRequestDTO> ongoing = wish.getWishRequestList().stream()
                .filter(wr -> "ONGOING".equals(wr.getStatus()))
                .findFirst();

        if (!ongoing.isEmpty()) {
            wish.setStatus("ONGOING");
        } else {
            wish.setStatus("OPEN");
        }
    }

    @Override
    public Page<Wish> search(WishSearchRequest req) {
        return repo.searchWishes(req);
    }

    private void validate(WishDTO dto) {
        if ("PAID".equals(dto.getRateType())) {
            if (dto.getAmount() == null) {
                throw new IllegalArgumentException("Amount must be provided for PAID wishes");
            }
            if (dto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Amount cannot be negative");
            }
        } else {
            // For FREE wishes, ensure amount is null
            dto.setAmount(null);
        }
    }

    private List<String>  populateCategories(WishDTO dto) {
        List<String> categories = aiService.generateCategories(dto.getTitle(), dto.getBody(), 5);
        return normalizeCategories(categories);
    }

    public List<String> normalizeCategories(List<String> categories) {
        return categories.stream()
                .map(String::toLowerCase)
                .map(c -> c.replaceAll("[^a-z0-9 ]", "")) // remove symbols
                .map(c -> c.replace(" ", "")) // hashtag style
                .distinct()
                .toList();
    }


}
