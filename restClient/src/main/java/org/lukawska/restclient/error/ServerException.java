package org.lukawska.restclient.error;

public class ServerException extends RuntimeException {
	public ServerException(String errorBody) {
      super("Server error: " + errorBody);
	}
}
