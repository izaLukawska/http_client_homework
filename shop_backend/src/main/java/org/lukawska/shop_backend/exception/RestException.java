package org.lukawska.shop_backend.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

	private final ExceptionEnum exceptionEnum;

	public RestException(ExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMessage());
		this.exceptionEnum = exceptionEnum;
	}
}
