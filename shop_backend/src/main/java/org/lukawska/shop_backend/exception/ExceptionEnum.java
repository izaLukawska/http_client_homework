package org.lukawska.shop_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {

	PRODUCT_ALREADY_EXISTS("Product already exists", HttpStatus.CONFLICT),
	PRODUCT_NOT_FOUND("No such product exists", HttpStatus.NOT_FOUND),
	ORDER_NOT_FOUND("No such order exists", HttpStatus.NOT_FOUND),
	ORDER_ALREADY_EXISTS("Order already exists", HttpStatus.CONFLICT),
	CUSTOMER_NOT_FOUND("No such customer exists", HttpStatus.NOT_FOUND),
	CUSTOMER_ALREADY_EXISTS("Customer already exists", HttpStatus.CONFLICT),
	USER_NOT_FOUND("No such customer exists", HttpStatus.NOT_FOUND);

	private final String message;
	private final HttpStatus httpStatus;
}
