package com.cstify.common.response;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.snc.gift")
public class SuccessResAdvice<T> implements ResponseBodyAdvice<T> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		int status = servletResponse.getStatus();
		HttpStatus resolve = HttpStatus.resolve(status);
		
		if(status > 600) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} 
		
		if (resolve == null) {
			return body;
		}

		if (!resolve.is2xxSuccessful()) {
			if(body == null) {
				return (T) new SimpleBody(status, "OK");
			} else {
				if( body instanceof SimpleBody) {
					return body;
				} else {
					return (T) new DataBody(status, "OK", body);
				}				
			}			
		}
		
		return body;
	}
}
