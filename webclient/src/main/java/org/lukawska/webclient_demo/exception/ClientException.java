package org.lukawska.webclient_demo.exception;

public class ClientException extends RuntimeException {

	public ClientException(String errorBody) {
		super("Client error: " + errorBody);
	}
}