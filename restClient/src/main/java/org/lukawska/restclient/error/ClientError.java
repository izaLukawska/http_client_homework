package org.lukawska.restclient.error;

public class ClientError extends RuntimeException {
	public ClientError(String errorBody) {
      super("Client error: " + errorBody);
	}
}
