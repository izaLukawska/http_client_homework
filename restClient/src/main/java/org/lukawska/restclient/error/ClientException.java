package org.lukawska.restclient.error;

public class ClientException extends RuntimeException {
	public ClientException(String errorBody) {
      super("Client error: " + errorBody);
	}
}
