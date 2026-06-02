package com.cstify.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
		log.error("==============>>>>>>>>>>>> THREAD ERROR");
        log.error("Exception Message :: {}", throwable.getMessage(), throwable);
        log.error("Method Name :: {}", method.getName());
		for (Object param : obj) {
            log.error("Parameter Value :: {}", param);
		}
		log.error("==============>>>>>>>>>>>> THREAD ERROR END");
	}
}
