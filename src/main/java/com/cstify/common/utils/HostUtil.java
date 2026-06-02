package com.cstify.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public final class HostUtil {

    private HostUtil() {
        // Utility class
    }

    /**
     * Docker + Nginx 환경에서 실제 외부 접근 Base URL 반환
     *
     * 우선순위:
     * 1. X-Forwarded-Proto
     * 2. Host
     *
     * 예:
     * https://example.com
     * http://localhost:8080
     */
    public static String getBaseUrl(HttpServletRequest request) {

        // 1. Protocol (https / http)
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isEmpty()) {
            scheme = request.getScheme();
        }

        // 2. Host (domain or ip)
        String host = request.getHeader("Host");
        if (host == null || host.isEmpty()) {
            host = request.getServerName();
        }

        return scheme + "://" + host;
    }

    /**
     * NICE 인증용 Redirect URL 생성
     */
    public static String getNiceReturnUrl(HttpServletRequest request) {
        return getBaseUrl(request) + "/nice/return.html";
    }

    public static String getNiceCloseUrl(HttpServletRequest request) {
        return getBaseUrl(request) + "/nice/close.html";
    }
}
