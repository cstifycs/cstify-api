package com.cstify.common.annotation;

import com.cstify.common.util.RequestUtil;
import com.cstify.common.vo.SecurityUserDetails;
import com.cstify.common.vo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final static String HEADER_CHANNEL = "X-Channel";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isAnnotation = parameter.getParameterAnnotation(CustomAnnotation.User.class) != null;
		boolean isUserClass = UserInfo.class.equals(parameter.getParameterType());
		return isAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		HttpServletRequest httpServletRequest = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class));
        String channel =  httpServletRequest.getHeader(HEADER_CHANNEL) == null ? "unknown" : httpServletRequest.getHeader(HEADER_CHANNEL);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof SecurityUserDetails(UserInfo userInfo)) {
            userInfo.setChannel(channel);
			userInfo.setDomain(httpServletRequest.getServerName());
			userInfo.setClientIP(RequestUtil.getClientIP(httpServletRequest));
			return userInfo;
		}

		// 그 외 처리
		UserInfo guestUser = new UserInfo();
		guestUser.setLoginId("guest");
		guestUser.setUserName("Guest");
		guestUser.setChannel(channel);
		guestUser.setDomain(httpServletRequest.getServerName());
		guestUser.setClientIP(RequestUtil.getClientIP(httpServletRequest));
		return guestUser;
	}
}
