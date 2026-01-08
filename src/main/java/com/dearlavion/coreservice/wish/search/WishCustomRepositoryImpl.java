package com.dearlavion.coreservice.wish.search;

import lombok.RequiredArgsConstructor;
import com.dearlavion.coreservice.wish.Wish;
import org.bson.types.Decimal128;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class WishCustomRepositoryImpl implements WishCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Wish> searchWishes(WishSearchRequest req) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        // Apply title keyword search
        boolean emptyPage = applyTitleKeyword(query, criteriaList, req);
        if (emptyPage) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        // Rate
        applyRateCriteria(criteriaList, req);
        // Date
        applyStartDateCriteria(criteriaList, req);
        //Other fields
        applyFieldFilter(criteriaList, "location", req.getLocation(), true);
        applyFieldFilter(criteriaList, "status", req.getStatus(), false);
        applyFieldFilter(criteriaList, "username", req.getUsername(), false);
        applyFieldFilter(criteriaList, "categories", req.getCategories(), false);

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Wish.class);

        query.with(pageable);
        query.with(determineSort(req));

        List<Wish> result = mongoTemplate.find(query, Wish.class);

        return new PageImpl<>(result, pageable, total);
    }

    private void applyFieldFilter(List<Criteria> criteriaList, String fieldName, Object value, boolean regex) {
        if (value == null) return;
        if (value instanceof String s && s.isBlank()) return;

        if (regex && value instanceof String str) {
            criteriaList.add(Criteria.where(fieldName).regex(Pattern.quote(str), "i"));
        } else {
            criteriaList.add(Criteria.where(fieldName).is(value));
        }
    }

    private Sort determineSort(WishSearchRequest req) {
        String sortBy = req.getSortBy();

        if (sortBy == null || sortBy.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        return switch (sortBy) {
            case "startDateAsc" -> Sort.by(Sort.Direction.ASC, "startDate");
            case "startDateDesc" -> Sort.by(Sort.Direction.DESC, "startDate");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }

    private void applyRateCriteria(List<Criteria> criteriaList, WishSearchRequest req) {
        if (req.getRateType() != null && !req.getRateType().isBlank()) {
            // Filter by rateType
            criteriaList.add(Criteria.where("rateType").is(req.getRateType()));

            // Amount filters only if PAID
            if ("PAID".equals(req.getRateType())) {
                if (req.getAmountFrom() != null) {
                    criteriaList.add(Criteria.where("amount").gte(new Decimal128(req.getAmountFrom())));
                }
                if (req.getAmountTo() != null) {
                    criteriaList.add(Criteria.where("amount").lte(new Decimal128(req.getAmountTo())));
                }
            }
        }
    }

    private boolean applyTitleKeyword(Query query, List<Criteria> criteriaList, WishSearchRequest req) {
        if (req.getKeyword() == null || req.getKeyword().isBlank()) {
            return false; // no keyword, continue normally
        }

        String keyword = req.getKeyword().trim();

        // Minimum length check
        if (keyword.length() < 2) {
            // Return empty page immediately
            return true;
        }

        if (keyword.length() < 4) {
            // Prefix / incomplete word search (regex)
            criteriaList.add(Criteria.where("title").regex(Pattern.quote(keyword), "i"));
        } else {
            // Full-text search (requires MongoDB text index)
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(keyword);
            query.addCriteria(textCriteria);
        }

        return false; // continue normally
    }


    private void applyStartDateCriteria(List<Criteria> criteriaList, WishSearchRequest req) {
        Date now = new Date();

        // Default: past 6 months
        Date startDateFrom = new Date(now.getTime() - 180L * 24 * 60 * 60 * 1000);

        // Override if user provided a filter
        if (req.getStartDateFrom() != null) {
            startDateFrom = Date.from(req.getStartDateFrom());
        }

        // Default: +1 year
        Date startDateTo = new Date(now.getTime() + 365L * 24 * 60 * 60 * 1000);
        if (req.getStartDateTo() != null) {
            startDateTo = Date.from(req.getStartDateTo());
        }

        // Add Criteria directly to the list
        criteriaList.add(Criteria.where("startDate").gte(startDateFrom).lte(startDateTo));
    }
}
