package com.dearlavion.coreservice.wish.cache;

import com.dearlavion.coreservice.wish.Wish;
import com.dearlavion.coreservice.wish.search.WishSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.HASH_KEY;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.RATE_TYPE;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.STATUS;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.TITLE;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.extractTokens;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.setFieldKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishSearchCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public List<Wish> search(WishSearchRequest req) {
        List<String> keys = buildKeys(req);

        if (keys.isEmpty()) {
            return List.of();
        }

        Set<String> candidateIds = intersect(keys);

        if (candidateIds == null || candidateIds.isEmpty()) {
            return List.of();
        }

        return fetchWishes(candidateIds);
    }

    /* ---------------- PRIVATE ---------------- */

    private List<String> buildKeys(WishSearchRequest req) {
        List<String> keys = new ArrayList<>();

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            keys.add(setFieldKey(STATUS, req.getStatus().toLowerCase()));
        }

        if (req.getRateType() != null && !req.getRateType().isBlank()) {
            keys.add(setFieldKey(RATE_TYPE, req.getRateType().toLowerCase()));
        }

        if (req.getKeyword() != null && req.getKeyword().length() >= 3) {
            extractTokens(req.getKeyword())
                    .forEach(t -> keys.add(setFieldKey(TITLE, t)));
        }

        return keys;
    }

    private Set<String> intersect(List<String> keys) {
        if (keys.size() == 1) {
            return stringRedisTemplate.opsForSet().members(keys.get(0));
        }

        return stringRedisTemplate.opsForSet()
                .intersect(keys.get(0), keys.subList(1, keys.size()));
    }

    private List<Wish> fetchWishes(Set<String> ids) {

        if (ids == null || ids.isEmpty()) {
            log.debug("[CACHE] No candidate IDs found");
            return List.of();
        }

        log.debug("[CACHE] Fetching wishes from hash. Candidate IDs: {}", ids);

        List<Object> values =
                redisTemplate.opsForHash()
                        .multiGet(HASH_KEY, new ArrayList<>(ids));

        List<Wish> wishes = values.stream()
                .filter(Objects::nonNull)
                .map(v -> (Wish) v)
                .toList();

        // Log fetched wish IDs
        List<String> fetchedIds = wishes.stream()
                .map(Wish::getId)
                .toList();

        log.info("[CACHE HIT] Wishes fetched: count={}, ids={}",
                fetchedIds.size(),
                fetchedIds
        );

        return wishes;
    }

}

