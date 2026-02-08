package com.dearlavion.coreservice.common.cache;

import com.dearlavion.coreservice.wish.cache.WishCacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void putMap(String key, Object value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds, java.util.concurrent.TimeUnit.SECONDS);
    }

    public Object getMap(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean existInMap(String key) {
        return redisTemplate.hasKey(key);
    }

    public void putHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getFromHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void removeFromHash(String key, String hashKey){
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public void updateHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void putSet(String name, String value, String id) {
        String fieldValue = WishCacheUtil.formatToString(value);
        if (fieldValue != null) {
            redisTemplate.opsForSet().add(WishCacheUtil.setFieldKey(name, fieldValue), id);
        }
    }
}

