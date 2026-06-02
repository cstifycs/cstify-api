package com.cstify.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder()
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .build();
//    }

    @Bean
    public WebClient webClient() {
        try {
            // SSL 세션 재사용 방지 (아까 발생한 SSL 에러 해결책)
            SslContext sslContext = SslContextBuilder.forClient()
                    .sessionCacheSize(0)
                    .sessionTimeout(0)
                    .build();

            // HttpClient를 명시적으로 생성
            HttpClient httpClient = HttpClient.create()
                    .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

            return WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("WebClient SSL 설정 실패", e);
        }
    }
}
