//package com.cstify.config;
//
//import com.cstify.common.provider.TokenProvider;
//import com.cstify.common.service.SecurityUserDetailsService;
//import com.cstify.common.service.TokenBlockService;
//import com.cstify.auth.security.CustomAccessDeniedHandler;
//import com.cstify.auth.security.CustomAuthEntryPoint;
//import com.cstify.auth.security.JwtAuthFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//	@Autowired
//	private CorsConfigurationSource corsConfigurationSource;
//
//	private final SecurityUserDetailsService userDetailsService;
//	private final TokenBlockService tokenBlockService;
//	private final TokenProvider tokenProvider;
//
//	private final CustomAccessDeniedHandler customAccessDeniedHandler;
//	private final CustomAuthEntryPoint customAuthEntryPoint;
//
//	private static final String[] permitAllArray = {
//			"/", "/api-docs/**", "/swagger-ui/**",
//			"/api/systems/admin", "/api/auth/token",
//			"/api/auth/member/sign-in","/api/auth/pgadmin/sign-in","/api/auth/malladmin/sign-in",
//			"/api/auth/email-verifications/**",
//			"/api/auth/member/find-id", "/api/auth/member/password/reset",
//
//			"/api/banks/**", "/api/companies/codes/**",
//			"/api/mall/members/sign-up", "/api/mall/addresses/search", "/api/mall/partnership-inquiries",
//			"/api/mall/sites/**", "/api/mall/displays/**", "/api/mall/notices/**", "/api/mall/faqs/**", "/api/mall/products/**",
//			"/api/images/**",
//
//			"/api/pgadmin/virtual-accounts/user"
//	};
//
//	@Bean
//	@Order(1)
//	public SecurityFilterChain publicChain(HttpSecurity http) {
//		http.cors(cors -> cors.configurationSource(corsConfigurationSource))
//			.securityMatcher(permitAllArray)
//			.csrf(AbstractHttpConfigurer::disable)
//			.formLogin(AbstractHttpConfigurer::disable)
//			.httpBasic(AbstractHttpConfigurer::disable)
//			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//			.exceptionHandling(handler -> handler
//				.authenticationEntryPoint(customAuthEntryPoint)
//				.accessDeniedHandler(customAccessDeniedHandler));
//		return http.build();
//	}
//
//	@Bean
//	@Order(2)
//	public SecurityFilterChain securityChain(HttpSecurity http) {
//		http.cors(cors -> cors.configurationSource(corsConfigurationSource))
//			.csrf(AbstractHttpConfigurer::disable)
//			.formLogin(AbstractHttpConfigurer::disable)
//			.httpBasic(AbstractHttpConfigurer::disable)
//			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			.authorizeHttpRequests(auth -> auth
//				.requestMatchers("/api/mall/**").hasAnyRole("MEMBER")
//				.requestMatchers("/api/admins/**").hasAnyRole("HQ_MALL","SUPER_HQ","SYSTEM")
//				.requestMatchers("/api/pgadmin/**", "/api/pgadmins/**").hasAnyRole("MERCHANT","AGENT","DISTRIBUTOR","HQ_PG","SUPER_HQ","SYSTEM")
//				.requestMatchers(
//						"/api/malladmin/reviews", "/api/malladmin/reviews/**",
//						"/api/malladmins/reviews", "/api/malladmins/reviews/**")
//					.hasAnyRole("MERCHANT","HQ_MALL","SUPER_HQ","SYSTEM")
//				.requestMatchers("/api/malladmin/**", "/api/malladmins/**").hasAnyRole("HQ_MALL","SUPER_HQ","SYSTEM")
//				.requestMatchers("/api/systems/**").hasRole( "SYSTEM")
//                // NICE 관련 + static HTML 전부 허용
//                .requestMatchers( "/**/*.html").permitAll()
//				.anyRequest().authenticated()
//			)
//			.exceptionHandling(handler -> handler
//				.authenticationEntryPoint(customAuthEntryPoint)
//				.accessDeniedHandler(customAccessDeniedHandler))
//			.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	}
//
//	private JwtAuthFilter jwtAuthFilter(){
//		return new JwtAuthFilter(userDetailsService, tokenBlockService, tokenProvider);
//	}
//}
