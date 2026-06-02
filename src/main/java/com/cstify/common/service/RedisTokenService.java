package com.cstify.common.service;

import com.cstify.common.provider.RedisProvider;
import com.cstify.common.provider.RedisTokenProvider;
import com.cstify.common.provider.TokenProvider;
import com.cstify.common.util.RequestUtil;
import com.cstify.common.vo.RefreshTokenPayload;
import com.cstify.common.vo.TokenPayload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    @Value("${security.token.access.validity}")
    private long accessTokenValidity;

    @Value("${security.token.refresh.validity}")
    private long refreshTokenValidity;

    @Value("${security.token.redis.ttl.throttle-interval}")
    private long throttleInterval;

    private final RedisProvider redisProvider;

    private final RedisTokenProvider redisTokenProvider;

    private final TokenProvider tokenProvider;

    private static final String USER_PREFIX = "userSession:";

    private static final String REFRESH_TOKEN_PREFIX = "rt:";

    private static final String TOKEN_PREFIX = "token:";

    private static final String BLACKLIST_PREFIX = "accessToken:blacklist:";

    private String getUserKey(Long userNo) {
        return USER_PREFIX + userNo;
    }

    private String getTokenKey(String tokenKey) {
        return TOKEN_PREFIX + tokenKey;
    }

    private String getRefreshTokenKey(String refreshTokenKey) {
        return REFRESH_TOKEN_PREFIX + refreshTokenKey;
    }

    private String getBlacklistKey(String blacklistKey) {
        return BLACKLIST_PREFIX + blacklistKey;
    }

    public void saveTokens(String tokenKey, String refreshTokenKey, String accessToken, String refreshToken, HttpServletRequest request) {
        String ipAddress = RequestUtil.getClientIP(request);
        String userAgent = request.getHeader("User-Agent");
        String deviceId = request.getHeader("X-Device-Id");
        String country = "KR";

        Long userNo = tokenProvider.getUserNo(accessToken);

        Duration accessTokenTTL = Duration.ofSeconds(accessTokenValidity);
        Duration refreshTokenTTL = Duration.ofSeconds(refreshTokenValidity);

        // 1. Access TokenPayload 저장
        TokenPayload payload = new TokenPayload(accessToken, refreshTokenKey, ipAddress, userAgent, deviceId, country, Instant.now().getEpochSecond());
        redisTokenProvider.setToken(getTokenKey(tokenKey), payload, accessTokenTTL);

        // 2. RefreshToken 저장
        saveRefreshToken(refreshTokenKey, refreshToken, tokenKey, refreshTokenTTL);

        // 3. userSession 저장
        redisTokenProvider.setSession(getUserKey(userNo), tokenKey, refreshTokenTTL);
    }

    public TokenPayload getTokens(String tokenKey) {
        if(tokenKey == null) return null;
        TokenPayload payload = redisTokenProvider.getToken(getTokenKey(tokenKey));
        if (payload != null) {
            long now = Instant.now().getEpochSecond();
            long lastTouched = payload.getLastTouched() != null ? payload.getLastTouched() : 0L;
            // TTL 갱신 여부 판단 (Throttle)
            if (now - lastTouched >= Duration.ofSeconds(throttleInterval).getSeconds()) {
                payload.setLastTouched(now);
                redisTokenProvider.setToken(getTokenKey(tokenKey), payload, Duration.ofSeconds(accessTokenValidity));
            }
        }
        return payload;
    }

    public Set<String> getSession(Long userNo) {
        return redisTokenProvider.getSession(getUserKey(userNo));
    }

//    public String getTokenKey(Long userNo){
//        Object tokenKey = redisProvider.get(getUserKey(userNo));
//        return tokenKey != null ? tokenKey.toString() : null;
//    }

    public void deleteTokens(String tokenKey) {
        redisTokenProvider.deleteToken(getTokenKey(tokenKey));
    }

    public void blacklistAccessToken(String jti, long ttlSeconds) {
        redisProvider.set(getBlacklistKey(jti), "logout", Duration.ofSeconds(ttlSeconds));
    }

    public boolean isBlacklisted(String jti) {
        if (jti == null || jti.isBlank()) return false;
        return redisProvider.hasKey(getBlacklistKey(jti));
    }

    public void saveRefreshToken(String refreshTokenKey, String refreshToken, String tokenKey, Duration ttl) {
        RefreshTokenPayload refreshTokenPayload = new RefreshTokenPayload(refreshToken, tokenKey);
        redisTokenProvider.setRefresh(getRefreshTokenKey(refreshTokenKey), refreshTokenPayload, ttl);
    }

    public RefreshTokenPayload getRefreshToken(String refreshTokenKey) {
        return redisTokenProvider.getRefresh(getRefreshTokenKey(refreshTokenKey));
    }

    public void deleteRefreshToken(String refreshTokenKey) {
        redisTokenProvider.deleteRefresh(getRefreshTokenKey(refreshTokenKey));
    }

    public boolean isRefreshTokenValid(String refreshTokenKey, String refreshToken) {
        RefreshTokenPayload cachedToken = getRefreshToken(refreshTokenKey);
        return cachedToken != null && cachedToken.getRefreshToken().equals(refreshToken);
    }

    public void deleteUserSession(Long userNo, String tokenKey) {
        redisTokenProvider.removeSession(getUserKey(userNo), tokenKey);
    }

    public void deleteAllUserSession(Long userNo) {
        redisTokenProvider.deleteAllSessions(getUserKey(userNo));
    }
}
