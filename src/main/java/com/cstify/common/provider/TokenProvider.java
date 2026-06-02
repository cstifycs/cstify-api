package com.cstify.common.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cstify.common.exception.RefreshTokenExpiredException;
import com.cstify.common.exception.TokenExpiredException;
import com.cstify.common.exception.TokenInvalidException;
import com.cstify.common.util.CodeUtil;
import com.cstify.common.vo.SecurityUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${security.token.key}")
    private String securityKey;

    @Value("${security.token.issuer}")
    private String tokenIssuer;

    @Value("${security.token.access.validity}")
    private long accessTokenValidity;

    @Value("${security.token.refresh.validity}")
    private long refreshTokenValidity;

    private SecretKey key;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));
    }

    public String getJti(String token) {
        Claims claims = getClaims(token);
        return claims.getId(); // "jti" 클레임 값
    }

    public String createAccessToken(SecurityUserDetails user, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(Objects.nonNull(jti) ? jti : CodeUtil.jti())
                .subject(user.userInfo().getUserId().toString())
                .claim("roles", user.userInfo().getRoles())
                .issuedAt(Date.from(now))
                .issuer(tokenIssuer)
                .expiration(Date.from(now.plusSeconds(accessTokenValidity)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String createAccessToken(Long userNo, List<String> roles, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(Objects.nonNull(jti) ? jti : CodeUtil.jti())
                .subject(userNo.toString())
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .issuer(tokenIssuer)
                .expiration(Date.from(now.plusSeconds(accessTokenValidity)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(SecurityUserDetails user, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(Objects.nonNull(jti) ? jti : CodeUtil.jti())
                .subject(user.userInfo().getUserId().toString())
                .claim("roles", user.userInfo().getRoles())
                .issuedAt(Date.from(now))
                .issuer(tokenIssuer)
                .expiration(Date.from(now.plusSeconds(refreshTokenValidity)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(Long userNo, List<String> roles, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(Objects.nonNull(jti) ? jti : CodeUtil.jti())
                .subject(userNo.toString())
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .issuer(tokenIssuer)
                .expiration(Date.from(now.plusSeconds(refreshTokenValidity)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();  // 만료 토큰 → ExpiredJwtException 그대로 던짐
        } catch (IllegalArgumentException | JwtException e) {
            throw new TokenInvalidException("INVALID_TOKEN");
        }
    }

    public void validateRefreshToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();  // 만료 토큰 → ExpiredJwtException 그대로 던짐
        } catch (IllegalArgumentException | JwtException e) {
            throw new RefreshTokenExpiredException();
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public Long getUserNo(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public List<String> getRoles(String token) {
        Object roles = getClaims(token).get("roles");
        return objectMapper.convertValue(roles, new TypeReference<List<String>>() {});
    }

    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    public long getRemainingValidity(String token) {
        Claims claims = getClaims(token);
        long expiration = claims.getExpiration().getTime() - System.currentTimeMillis();
        if(expiration < 0) expiration = 0;
        return expiration/1000;
    }
}
