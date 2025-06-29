package org.lukawska.webclient_with_mockwebserver.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(Long id) {
		super("Resource not found for id: " + id);
	}
}
