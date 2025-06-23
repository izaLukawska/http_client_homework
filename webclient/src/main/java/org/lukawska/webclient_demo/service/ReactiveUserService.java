package org.lukawska.webclient_demo.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.webclient_demo.dto.UserResponse;
import org.lukawska.webclient_demo.exception.ClientException;
import org.lukawska.webclient_demo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.rmi.ServerException;

@Service
@RequiredArgsConstructor
public class ReactiveUserService {

	private final WebClient webClient;

	public Mono<UserResponse> getUserWithErrorHandling(Long id) {
		return webClient.get()
				.uri("/users/{id}", id)
				.retrieve()
				.onStatus(HttpStatus.NOT_FOUND::equals,
						response -> Mono.error(new UserNotFoundException(id)))
				.onStatus(HttpStatusCode::is4xxClientError, response ->
						response.bodyToMono(String.class)
								.map(ClientException::new)
				)
				.onStatus(HttpStatusCode::is5xxServerError, response ->
						response.bodyToMono(String.class)
								.map(ServerException::new)
				)
				.bodyToMono(UserResponse.class);
	}
}