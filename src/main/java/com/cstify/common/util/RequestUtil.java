package com.cstify.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class RequestUtil {
    public static String getAuthorizationToken(HttpServletRequest request) {
        String token = null;
        String tokenHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(tokenHeader)) {
            token = tokenHeader.replace("Bearer ", "");
        }
        return token;
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null) ip = request.getRemoteAddr();

        if ("0:0:0:0:0:0:0:1".equals(ip)) { //local ip 가 ipv6 일 경우 ipv4로 변경
            return "127.0.0.1";
        }
        return ip;
    }

    public static String getCookieName(HttpServletRequest request) {
        String channel = request.getHeader("X-Channel") != null ? request.getHeader("X-Channel") : "default" ;
        return "tokenKey-" + channel.trim().toLowerCase();
    }

    public static String getDomainName(HttpServletRequest request) {
        String host = request.getServerName();
        return host.indexOf('.') != host.lastIndexOf('.')
                ? host.substring(host.indexOf('.') + 1)
                : host;
    }

    public static String extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public static boolean isIpSimilar(String storedIp, String currentIp) {
        String[] storedParts = storedIp.split("\\.");
        String[] currentParts = currentIp.split("\\.");

        if (storedParts.length < 2 || currentParts.length < 2) return false;

        // 앞 2 옥텟 비교
        return storedParts[0].equals(currentParts[0]) && storedParts[1].equals(currentParts[1]);
    }

    public static String hashUserAgent(String ua) {
        // 간단한 해시 or 디바이스 정보만 추출
        return DigestUtils.sha256Hex(ua.toLowerCase());
    }
}
