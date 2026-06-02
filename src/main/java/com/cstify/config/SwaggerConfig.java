package com.cstify.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
	
	private static final String API_NAME = "SNC API";
	private static final String API_VERSION = "1.0.0";
	private static final String API_DESCRIPTION = "SNC API 명세서";


	@Bean
	public OpenAPI openAPI(){
		SecurityScheme securityScheme = new SecurityScheme()
				.name("AccessToken")
				.type(SecurityScheme.Type.HTTP)
				.in(SecurityScheme.In.HEADER)
				.scheme("bearer")
				.bearerFormat("JWT");

		return new OpenAPI()
				.info(new Info().title(API_NAME).description(API_DESCRIPTION).version(API_VERSION))
				.security(Collections.singletonList(new SecurityRequirement().addList("AccessToken")))
				.components(new Components()
//						.addParameters("X-Tenant", new Parameter()
//								.in(ParameterIn.HEADER.toString())
//								.name("X-Tenant")
//								.description("Tenant Code")
//								.schema(new StringSchema())
//								.required(false)
//						)
//						.addParameters("X-Site", new Parameter()
//								.in(ParameterIn.HEADER.toString())
//								.name("X-Site")
//								.description("Site Code")
//								.schema(new StringSchema())
//								.required(false)
//						)
//						.addParameters("X-Channel", new Parameter()
//								.in(ParameterIn.HEADER.toString())
//								.name("X-Channel")
//								.description("Channel")
//								.schema(new StringSchema())
//								.required(false)
//						)
						.addSecuritySchemes("AccessToken", securityScheme)
				);
    }

//	@Bean
//	public OpenAPI openAPI() {
//
//		// JWT Bearer
//		SecurityScheme accessTokenScheme = new SecurityScheme()
//				.type(SecurityScheme.Type.HTTP)
//				.scheme("bearer")
//				.bearerFormat("JWT")
//				.in(SecurityScheme.In.HEADER)
//				.name("Authorization");
//
//		// X-Tenant
//		SecurityScheme tenantScheme = new SecurityScheme()
//				.type(SecurityScheme.Type.APIKEY)
//				.in(SecurityScheme.In.HEADER)
//				.name("X-Tenant");
//
//		// X-Site
//		SecurityScheme siteScheme = new SecurityScheme()
//				.type(SecurityScheme.Type.APIKEY)
//				.in(SecurityScheme.In.HEADER)
//				.name("X-Site");
//
//		// X-Channel
//		SecurityScheme channelScheme = new SecurityScheme()
//				.type(SecurityScheme.Type.APIKEY)
//				.in(SecurityScheme.In.HEADER)
//				.name("X-Channel");
//
//
//		return new OpenAPI()
//				.info(new Info().title(API_NAME).version(API_VERSION).description(API_DESCRIPTION))
//				.components(new Components()
//						.addSecuritySchemes("AccessToken", accessTokenScheme)
//						.addSecuritySchemes("X-Tenant", tenantScheme)
//						.addSecuritySchemes("X-Site", siteScheme)
//						.addSecuritySchemes("X-Channel", channelScheme)
//				)
//				.security(List.of(
//						new SecurityRequirement().addList("AccessToken"),
//						new SecurityRequirement().addList("X-Tenant"),
//						new SecurityRequirement().addList("X-Site"),
//						new SecurityRequirement().addList("X-Channel")
//				));
//	}
}
