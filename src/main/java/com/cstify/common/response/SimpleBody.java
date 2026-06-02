package com.cstify.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SimpleBody implements ResBody {
	private int code;
	private String message;
}
