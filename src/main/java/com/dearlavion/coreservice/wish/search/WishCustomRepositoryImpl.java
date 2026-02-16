package com.dearlavion.coreservice.wish.search;

import com.dearlavion.coreservice.common.cache.RedisProperties;
import com.dearlavion.coreservice.wish.cache.WishIndexCacheService;
import com.dearlavion.coreservice.wish.cache.WishSearchCacheService;
import lombok.RequiredArgsConstructor;
import com.dearlavion.coreservice.wish.Wish;
import org.bson.types.Decimal128;
import org.springframework.data.domain.*;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class WishCustomRepositoryImpl implements WishCustomRepository {

    private final MongoTemplate mongoTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final WishSearchCacheService cacheSearchService;
    private final WishIndexCacheService cacheIndexService;
    private final RedisProperties redisProperties;

    @Override
    public Page<Wish> searchWishes(WishSearchRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());

        if (redisProperties.isEnabled()) {
            // STEP 1: SEARCH CACHE
            List<Wish> cachedWishes = cacheSearchService.search(req);
            if (!cachedWishes.isEmpty()) {
                System.out.println("[CACHE] returning cached wishes: ");
                cachedWishes.stream()
                        .map(Wish::getId)
                        .forEach(System.out::println);
                List<Wish> paged = cachedWishes.stream().skip((long) req.getPage() * req.getSize())
                        .limit(req.getSize()).toList();

                return new PageImpl<>(paged, pageable, cachedWishes.size());
            }
        }

        // STEP 2: SEARCH DB
        return searchDatabase(req, pageable);
    }


    private Page<Wish> searchDatabase(WishSearchRequest req, Pageable pageable) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        // Apply title keyword search
        boolean emptyPage = applyTitleKeyword(query, criteriaList, req);
        if (emptyPage) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        // Rate
        applyRateCriteria(criteriaList, req);
        // Date
        applyStartDateCriteria(criteriaList, req);

        applyFieldFilter(criteriaList, "status", req.getStatus(), false);
        applyFieldFilter(criteriaList, "username", req.getUsername(), false);
        applyFieldFilter(criteriaList, "categories", req.getCategories(), false);

        // Country (prefer code, fallback to name)
        applyFieldFilter(criteriaList, "countryCode", req.getCountryCode(), false);
        applyFieldFilter(criteriaList, "countryName", req.getCountryName(), true);
        // City
        applyFieldFilter(criteriaList, "cityName", req.getCityName(), true);
        // Geo (near me)
        applyGeoCriteria(query, criteriaList, req);


        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Wish.class);

        query.with(pageable);
        query.with(determineSort(req));

        List<Wish> result = mongoTemplate.find(query, Wish.class);
        if (redisProperties.isEnabled() && !result.isEmpty()) {
            cacheIndexService.index(result);
        }

        return new PageImpl<>(result, pageable, total);
    }

    private void applyGeoCriteria(Query query, List<Criteria> criteriaList, WishSearchRequest req) {

        if (req.getGeoPoints() == null || req.getGeoPoints().length != 2) {
            return; // no geo search
        }

        double lng = req.getGeoPoints()[0];
        double lat = req.getGeoPoints()[1];
        double radiusKm = req.getRadiusKm() != null ? req.getRadiusKm() : 20.0; // default 20km

        Point userLocation = new Point(lng, lat);
        Distance distance = new Distance(radiusKm, Metrics.KILOMETERS);

        Criteria geoCriteria = Criteria.where("geoPoints")
                .nearSphere(userLocation)
                .maxDistance(distance.getNormalizedValue());

        criteriaList.add(geoCriteria);
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
