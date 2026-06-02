package com.cstify.common.exception;

import java.io.Serial;

public class PermissionException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 6751438757011035001L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public PermissionException(String message) {
		super(message);
	}
}
