package com.cstify.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenPayload {
    private String accessToken;
    private String refreshTokenKey;
    private String ipAddress;
    private String userAgent;
    private String deviceId;
    private String country;
    private Long lastTouched;
}
