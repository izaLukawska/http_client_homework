package org.lukawska.webclient_with_mockwebserver.exception;

public class ClientException extends RuntimeException {
	public ClientException(String errorBody) {
      super("Client exception for: " + errorBody);
	}
}
