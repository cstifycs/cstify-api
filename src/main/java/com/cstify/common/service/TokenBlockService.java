package com.cstify.common.service;

import com.cstify.common.exception.TokenBlacklistException;
import com.cstify.common.exception.TokenInvalidException;
import com.cstify.common.provider.TokenProvider;
import com.cstify.common.util.RequestUtil;
import com.cstify.common.vo.TokenPayload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlockService {
    private final TokenProvider tokenProvider;

    private final RedisTokenService redisTokenService;

    public void isBlocked(String accessToken) {
        String jti = tokenProvider.getJti(accessToken);
        if (redisTokenService.isBlacklisted(jti)) {
            throw new TokenBlacklistException("BLOCKED_TOKEN");
        }
    }

    public void blockToken(String accessToken, String tokenKey) {
        if (StringUtils.hasText(tokenKey)) {
            redisTokenService.deleteTokens(tokenKey);   // accessToken 삭제
        }
//        if (StringUtils.hasText(accessToken)) {
//            try {
//                long expiration = tokenProvider.getRemainingValidity(accessToken); // 남은 시간
//                String jti = tokenProvider.getJti(accessToken); // 토큰 ID (혹은 subject)
//                redisTokenService.blacklistAccessToken(jti, expiration); // 블랙리스트 등록
//            } catch (JwtException ignored) {
//                // invalid 토큰은 blacklist 불필요하므로 무시
//            }
//        }
    }

    public void blockRefreshToken(String accessToken) {
        // 주로 로그아웃할때 사용
        Long userNo = tokenProvider.getUserNo(accessToken);
        String tokenKey = tokenProvider.getJti(accessToken);
        TokenPayload tokenPayload = redisTokenService.getTokens(tokenKey);

        this.blockToken(accessToken, tokenKey);
        if (tokenPayload != null) {
            String refreshTokenKey = tokenPayload.getRefreshTokenKey();
            redisTokenService.deleteRefreshToken(refreshTokenKey);
        }
        redisTokenService.deleteUserSession(userNo, tokenKey);
    }

    public void blockAllUserSessions(Long userId) {
        Set<String> tokenKeys = redisTokenService.getSession(userId);
        if (tokenKeys == null || tokenKeys.isEmpty()) {
            return;
        }
        for (String tokenKey : tokenKeys) {
            TokenPayload tokenPayload = redisTokenService.getTokens(tokenKey);
            if (tokenPayload != null) {
                String refreshKey = tokenPayload.getRefreshTokenKey();
                if (refreshKey != null && !refreshKey.isBlank()) {
                    redisTokenService.deleteRefreshToken(refreshKey);
                }
            }
            redisTokenService.deleteTokens(tokenKey);
        }
        redisTokenService.deleteAllUserSession(userId);
    }

    public void blockDeviceUserSession(Long userNo, String deviceId) {
        Set<String> tokenKeys = redisTokenService.getSession(userNo);
        if (tokenKeys == null || tokenKeys.isEmpty() || deviceId == null || deviceId.isBlank()) {
            return;
        }
        for (String tokenKey : tokenKeys) {
            TokenPayload tokenPayload = redisTokenService.getTokens(tokenKey);
            if(tokenPayload != null) {
                if(deviceId.equals(tokenPayload.getDeviceId())) {
                    this.blockToken(tokenPayload.getAccessToken(), tokenKey);
                    redisTokenService.deleteRefreshToken(tokenPayload.getRefreshTokenKey());
                    redisTokenService.deleteUserSession(userNo, tokenKey);
                }
            }
        }
    }

    public void validateClientInfo(String tokenKey, Long userNo, TokenPayload payload, HttpServletRequest request) {
        String currentIp = RequestUtil.getClientIP(request);
        String currentUa = RequestUtil.hashUserAgent(request.getHeader("User-Agent"));

        if (!RequestUtil.isIpSimilar(payload.getIpAddress(), currentIp) || !RequestUtil.hashUserAgent(payload.getUserAgent()).equals(currentUa)) {
            log.warn("Possible token hijack: userNo={}, oldIP={}, newIP={}, oldDevice={}, newDevice={}",
                    userNo, payload.getIpAddress(), currentIp, payload.getUserAgent(), currentUa );

            // 관리자 Slack 알림

            // 사용자 알림 (이메일, Push 알림)

            // 토큰 블랙 리스트 등록
            this.blockToken(payload.getAccessToken(), tokenKey);

            throw new TokenInvalidException("CLIENT_INFO_MISMATCH");
        }
    }
}
