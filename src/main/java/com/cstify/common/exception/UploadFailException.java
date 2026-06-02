package com.cstify.common.exception;

import java.io.Serial;

public class UploadFailException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 2275553533421585565L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
