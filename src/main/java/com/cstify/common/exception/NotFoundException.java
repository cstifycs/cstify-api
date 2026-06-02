package com.cstify.common.exception;

import java.io.Serial;

public class NotFoundException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = -2335574300467657813L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
	
	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message);
	}
}
