package com.dearlavion.coreservice.wish.cache;

import com.dearlavion.coreservice.wish.Wish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.HASH_KEY;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.INDEX_FIELDS;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.TITLE;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.extractTokens;
import static com.dearlavion.coreservice.wish.cache.WishCacheUtil.setFieldKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishIndexCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public void index(List<Wish> wishes) {

        if (wishes == null || wishes.isEmpty()) {
            log.debug("[CACHE INDEX] No wishes to index");
            return;
        }

        log.info("[CACHE INDEX] Indexing {} wishes", wishes.size());

        wishes.forEach(this::indexSingle);

        List<String> ids = wishes.stream()
                .map(Wish::getId)
                .toList();

        log.info("[CACHE INDEX COMPLETE] Indexed wish IDs={}", ids);
    }


    public void indexSingle(Wish wish) {
        String id = wish.getId();

        // Field indexes
        INDEX_FIELDS.forEach((field, extractor) -> {
            String value = extractor.apply(wish);
            if (value != null && !value.isBlank()) {
                stringRedisTemplate.opsForSet().add(
                        setFieldKey(field, value.toLowerCase()),
                        id
                );
            }
        });

        // Title tokens
        extractTokens(wish.getTitle())
                .forEach(token ->
                        stringRedisTemplate.opsForSet()
                                .add(setFieldKey(TITLE, token), id)
                );

        // Hash storage
        redisTemplate.opsForHash()
                .put(HASH_KEY, id, wish);
    }
}

