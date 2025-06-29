package org.lukawska.restclient.error;

public class UserNotFound extends RuntimeException {
	public UserNotFound(Long id) {
		super("User not found for id: " + id);
	}
}
