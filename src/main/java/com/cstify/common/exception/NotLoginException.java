package com.cstify.common.exception;

import java.io.Serial;

public class NotLoginException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -7480917899565810917L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
