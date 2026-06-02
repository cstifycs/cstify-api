package com.cstify.common.provider;

import com.cstify.common.vo.RefreshTokenPayload;
import com.cstify.common.vo.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisTokenProvider {
    private final RedisTemplate<String, String> sessionTemplate;
    private final RedisTemplate<String, TokenPayload> tokenTemplate;
    private final RedisTemplate<String, RefreshTokenPayload> refreshTemplate;

    public void setSession(String key, String tokenKey, Duration ttl) {
        sessionTemplate.opsForSet().add(key, tokenKey);
        sessionTemplate.expire(key, ttl);
    }

    public Set<String> getSession(String key) {
        return sessionTemplate.opsForSet().members(key);
    }

    public void removeSession(String key, String tokenKey) {
        sessionTemplate.opsForSet().remove(key, tokenKey);
    }

    public void deleteAllSessions(String key) {
        sessionTemplate.delete(key);
    }

    public boolean existsSession(String key, String tokenKey) {
        return sessionTemplate.opsForSet().isMember(key, tokenKey);
    }

    public void setToken(String key, TokenPayload value, Duration ttl) {
        tokenTemplate.opsForValue().set(key, value, ttl);
    }

    public TokenPayload getToken(String key) {
        return tokenTemplate.opsForValue().get(key);
    }

    public void deleteToken(String key) {
        tokenTemplate.delete(key);
    }

    public void setRefresh(String key, RefreshTokenPayload value, Duration ttl) {
        refreshTemplate.opsForValue().set(key, value, ttl);
    }

    public RefreshTokenPayload getRefresh(String key) {
        return refreshTemplate.opsForValue().get(key);
    }

    public void deleteRefresh(String key) {
        refreshTemplate.delete(key);
    }
}
