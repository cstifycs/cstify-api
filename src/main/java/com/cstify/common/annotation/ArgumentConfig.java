package com.cstify.common.annotation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ArgumentConfig implements WebMvcConfigurer {

	private final UserArgumentResolver userArgumentResolver;
	private final ChannelArgumentResolver channelArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userArgumentResolver);
		resolvers.add(channelArgumentResolver);
	}
}
