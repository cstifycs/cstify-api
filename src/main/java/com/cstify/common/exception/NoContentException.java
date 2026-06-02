package com.cstify.common.exception;

import java.io.Serial;

public class NoContentException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = -2629487614156841402L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
	
	public NoContentException() {
		super();
	}
	
	public NoContentException(String message) {
		super(message);
	}
}
