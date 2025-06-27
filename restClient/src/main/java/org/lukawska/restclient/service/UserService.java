package org.lukawska.restclient.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.restclient.dto.UserRequest;
import org.lukawska.restclient.dto.UserResponse;
import org.lukawska.restclient.error.RestClientErrorHandler;
import org.lukawska.restclient.error.UserNotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final RestClient restClient;

	@Value("${jsonplaceholder.users-path:/users}")
	private String usersPath;

	public UserResponse createUser(UserRequest userRequest) {
		return RestClientErrorHandler.applyCommonErrorHandling(
						restClient.post()
								.uri(usersPath)
								.contentType(MediaType.APPLICATION_JSON)
								.body(userRequest)
								.retrieve())
				.body(UserResponse.class);
	}

	public UserResponse getUserById(Long id) {
		return RestClientErrorHandler.applyCommonErrorHandling(
						restClient.get()
								.uri(usersPath + "/{id}", id)
								.retrieve()
								.onStatus(status -> status == HttpStatus.NOT_FOUND,
										((request, response) -> {
											throw new UserNotFound(id);
										})))
				.body(UserResponse.class);
	}

	public List<UserResponse> getAllUsers() {
		return RestClientErrorHandler.applyCommonErrorHandling(
						restClient.get()
								.uri(usersPath)
								.retrieve())
				.body(new ParameterizedTypeReference<>() {
				});
	}

	public void deleteUserById(Long id) {
		RestClientErrorHandler.applyCommonErrorHandling(
						restClient.delete()
								.uri(usersPath + "/{id}", id)
								.retrieve()
								.onStatus(status -> status == HttpStatus.NOT_FOUND,
										((request, response) -> {
											throw new UserNotFound(id);
										})))
				.toBodilessEntity();
	}
}
