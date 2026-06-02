package com.cstify.common.exception;

import java.io.Serial;

public class BizException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = 1672357502111793501L;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
	
	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Integer code, String message) {
		super(String.valueOf(code) + '|' + message);
	}
}
