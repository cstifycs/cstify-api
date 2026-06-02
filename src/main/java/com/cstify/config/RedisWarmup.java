package com.cstify.config;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisWarmup {
    private final RedisConnectionFactory redisConnectionFactory;

    public RedisWarmup(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @PostConstruct
    public void warmup() {
        try (var conn = redisConnectionFactory.getConnection()) {
            conn.ping(); // Redis connection pool 초기화
        }
    }
}
