package com.cstify.common.exception;

import java.io.Serial;

public class CookieExpiredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8488903532611853020L;

    @Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
