package com.cstify.common.service;

import com.cstify.common.provider.RedisProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RedisSmsService {
    private static final long TOKEN_SAFETY_MARGIN_SECONDS = 600;
    private static final long MIN_TTL_SECONDS = 60;

    private static final String SMS_PREFIX = "sms:token:";

    private final RedisProvider redisProvider;

    private String getSmsKey(Long companyNo) {
        return SMS_PREFIX + companyNo;
    }

    public void saveSmsToken(Long companyNo, String smsToken, String expired) {
        long ttlSeconds = calcTtlSeconds(expired); // 만료까지 남은 초
        redisProvider.set(getSmsKey(companyNo), smsToken, Duration.ofSeconds(ttlSeconds));
    }

    public String getSmsToken(Long companyNo) {
        Object smsToken =  redisProvider.get(getSmsKey(companyNo));
        return smsToken != null ? smsToken.toString() : null;
    }

    private long calcTtlSeconds(String expired) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime expireAt = LocalDateTime.parse(expired, f);
        long ttl = Duration.between(LocalDateTime.now(), expireAt).getSeconds();
        return Math.max(ttl - TOKEN_SAFETY_MARGIN_SECONDS, MIN_TTL_SECONDS);
    }
}
