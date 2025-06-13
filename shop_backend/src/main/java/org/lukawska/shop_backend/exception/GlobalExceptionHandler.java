package org.lukawska.shop_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RestException.class)
	public ResponseEntity<ExceptionResponse> handleRestException(RestException exception) {
		return ResponseEntity
				.status(exception.getExceptionEnum().getHttpStatus())
				.body(
						new ExceptionResponse(
								exception.getMessage(),
								exception.getExceptionEnum().getHttpStatus().value()
						)
				);
	}
}
