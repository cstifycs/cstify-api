package com.cstify.common.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisBoundProvider {
    private final RedisTemplate<String, Object> redisTemplate;

    private BoundValueOperations<String, Object> getBoundValueOps(String key) {
        return redisTemplate.boundValueOps(key);
    }

    public void set(String key, String value, Duration ttl) {
        BoundValueOperations<String, Object> ops = getBoundValueOps(key);
        ops.set(value, ttl);
    }

    public void set(String key, String value) {
        getBoundValueOps(key).set(value);
    }

    public Object get(String key) {
        return getBoundValueOps(key).get();
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Duration getTTL(String key) {
        Long expire = redisTemplate.getExpire(key);
        return Duration.ofSeconds(expire);
    }
}
