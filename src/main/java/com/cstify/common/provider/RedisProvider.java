package com.cstify.common.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisProvider {
    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void setHashKey(String key, Map<String, String> value, Duration ttl) {
        redisTemplate.opsForHash().putAll(key, value);
        redisTemplate.expire(key, ttl);
    }

    public Map<Object, Object> getHashKey(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
}
