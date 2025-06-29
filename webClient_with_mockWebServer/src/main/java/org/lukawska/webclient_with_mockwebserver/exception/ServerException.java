package org.lukawska.webclient_with_mockwebserver.exception;

public class ServerException extends RuntimeException {
	public ServerException(String errorBody) {
		super("Server error: " + errorBody);
	}
}
