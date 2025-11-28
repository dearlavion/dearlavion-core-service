package com.dearlavion.coreservice.wish.search;

import lombok.RequiredArgsConstructor;
import com.dearlavion.coreservice.wish.Wish;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WishCustomRepositoryImpl implements WishCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Wish> searchWishes(WishSearchRequest req) {
        List<Criteria> criteriaList = new ArrayList<>();

        // Search keyword in title/body
        if (req.getKeyword() != null && !req.getKeyword().isEmpty()) {
            criteriaList.add(
                    new Criteria().orOperator(
                            Criteria.where("title").regex(req.getKeyword(), "i"),
                            Criteria.where("body").regex(req.getKeyword(), "i")
                    )
            );
        }

        if (req.getLocation() != null && !req.getLocation().isEmpty())
            criteriaList.add(Criteria.where("location").regex(req.getLocation(), "i"));

        if (req.getStatus() != null && !req.getStatus().isEmpty())
            criteriaList.add(Criteria.where("status").is(req.getStatus()));

        if (req.getRateType() != null && !req.getRateType().isEmpty())
            criteriaList.add(Criteria.where("rateType").is(req.getRateType()));

        if (req.getUsername() != null && !req.getUsername().isEmpty())
            criteriaList.add(Criteria.where("username").is(req.getUsername()));

        // Filter by categories
        if (req.getCategories() != null && !req.getCategories().isEmpty())
            criteriaList.add(Criteria.where("categories").in(req.getCategories()));

        // Date range filter
        /*if (req.getStartDateFrom() != null && req.getStartDateTo() != null) {
            criteriaList.add(Criteria.where("startDate")
                    .gte(req.getStartDateFrom()).lte(req.getStartDateTo()));
        }
        if (req.getStartDateFrom() != null && req.getStartDateTo() != null) {
            Date from = Date.from(req.getStartDateFrom().toInstant());
            Date to = Date.from(req.getStartDateTo().toInstant());
            criteriaList.add(Criteria.where("startDate").gte(from).lte(to));
        }*/
        criteriaList.add(
                Criteria.where("startDate").gte(req.getStartDateFrom()).lte(req.getStartDateTo())
        );

        Query query = new Query();
        if (!criteriaList.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));

        long total = mongoTemplate.count(query, Wish.class);

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Wish> result = mongoTemplate.find(query, Wish.class);

        return new PageImpl<>(result, pageable, total);
    }
}
