package com.cstify.common.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -2335574300467657813L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}
}
