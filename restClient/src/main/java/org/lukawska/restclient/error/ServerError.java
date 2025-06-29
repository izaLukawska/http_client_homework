package org.lukawska.restclient.error;

public class ServerError extends RuntimeException {
	public ServerError(String errorBody) {
      super("Server error: " + errorBody);
	}
}
