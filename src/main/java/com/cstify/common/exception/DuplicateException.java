package com.cstify.common.exception;

import java.io.Serial;

public class DuplicateException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = -1378657471285792585L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
	
	public DuplicateException() {
		super();
	}
	
	public DuplicateException(String message) {
		super(message);
	}
}
