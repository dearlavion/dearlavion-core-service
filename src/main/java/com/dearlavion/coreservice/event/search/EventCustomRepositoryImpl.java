package com.dearlavion.coreservice.event.search;

import com.dearlavion.coreservice.event.Event;
import lombok.RequiredArgsConstructor;
import org.bson.types.Decimal128;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Event> searchEvents(EventSearchRequest req) {

        List<Criteria> filters = new ArrayList<>();

        // Keyword search
        if (req.getKeyword() != null && !req.getKeyword().isEmpty()) {
            filters.add(
                    new Criteria().orOperator(
                            Criteria.where("title").regex(req.getKeyword(), "i"),
                            Criteria.where("body").regex(req.getKeyword(), "i")
                    )
            );
        }

        if (req.getLocation() != null && !req.getLocation().isEmpty()) {
            filters.add(Criteria.where("location").regex(req.getLocation(), "i"));
        }

        if (req.getStatus() != null && !req.getStatus().isEmpty()) {
            filters.add(Criteria.where("status").is(req.getStatus()));
        }

        if (req.getRateType() != null && !req.getRateType().isEmpty()) {
            filters.add(Criteria.where("rateType").is(req.getRateType()));

            if ("PAID".equals(req.getRateType())) {
                if (req.getAmountFrom() != null) {
                    filters.add(Criteria.where("amount")
                            .gte(new Decimal128(req.getAmountFrom())));
                }
                if (req.getAmountTo() != null) {
                    filters.add(Criteria.where("amount")
                            .lte(new Decimal128(req.getAmountTo())));
                }
            }
        }

        if (req.getOrganizer() != null && !req.getOrganizer().isEmpty()) {
            filters.add(Criteria.where("organizer").is(req.getOrganizer()));
        }

        if (req.getCategories() != null && !req.getCategories().isEmpty()) {
            filters.add(Criteria.where("categories").in(req.getCategories()));
        }

        // -------- DATE RANGE --------
        Date now = new Date();

        // Default show events within last 6 months
        Date defaultFrom = new Date(now.getTime() - 180L * 24 * 60 * 60 * 1000);
        Date startDateFrom = req.getStartDateFrom() != null
                ? Date.from(req.getStartDateFrom())
                : defaultFrom;

        Date startDateTo = req.getStartDateTo() != null
                ? Date.from(req.getStartDateTo())
                : new Date(now.getTime() + 365L * 24 * 60 * 60 * 1000); // +1 year

        filters.add(
                Criteria.where("startDate").gte(startDateFrom).lte(startDateTo)
        );

        // ----------------------------

        Query query = new Query();
        if (!filters.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(filters.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Event.class);

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        // Sorting
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        if ("startDateAsc".equals(req.getSortBy())) {
            sort = Sort.by(Sort.Direction.ASC, "startDate");
        } else if ("startDateDesc".equals(req.getSortBy())) {
            sort = Sort.by(Sort.Direction.DESC, "startDate");
        }

        query.with(pageable);
        query.with(sort);

        List<Event> items = mongoTemplate.find(query, Event.class);

        return new PageImpl<>(items, pageable, total);
    }
}
