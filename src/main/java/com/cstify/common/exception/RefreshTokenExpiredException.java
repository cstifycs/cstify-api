package com.cstify.common.exception;

import java.io.Serial;

public class RefreshTokenExpiredException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1380459457205367693L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
