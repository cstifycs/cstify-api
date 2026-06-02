package com.cstify.common.exception;

import java.io.Serial;

public class TokenBlacklistException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 5092879027937455060L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public TokenBlacklistException(String message) {
		super(message);
	}
}
