package com.cstify.common.response;

import org.springframework.http.ResponseEntity;

public class ResUtils {
	
	public static ResponseEntity<Object> json(int code, Object json) {
		return ResponseEntity.status(code).body(json);
	}
	
	public static ResponseEntity<Object> simple(int code, String message) {
		return ResponseEntity.status(code).body(new SimpleBody(code, message));
	}

	public static ResponseEntity<Object> simple(int status, int code, String message) {
		return ResponseEntity.status(status).body(new SimpleBody(code, message));
	}
	
	public static ResponseEntity<Object> data(int code, String message, Object data) {
		return ResponseEntity.status(code).body(new DataBody(code, message, data));
	}
}
