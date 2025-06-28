package org.lukawska.webclient_with_mockwebserver.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.webclient_with_mockwebserver.dto.UserRequest;
import org.lukawska.webclient_with_mockwebserver.dto.UserResponse;
import org.lukawska.webclient_with_mockwebserver.exception.CommonExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final WebClient webClient;

	private static final String USER_PATH = "/users";

	public Mono<UserResponse> getUserById(Long id) {
		return webClient.get()
				.uri(USER_PATH + "/{id}", id)
				.retrieve()
				.onStatus(HttpStatus.NOT_FOUND::equals,
						clientResponse -> CommonExceptionHandler.handleNotFound(id))
				.onStatus(HttpStatusCode::is4xxClientError, CommonExceptionHandler::handle4xxClientError)
				.onStatus(HttpStatusCode::is5xxServerError, CommonExceptionHandler::handle5xxServerError)
				.bodyToMono(UserResponse.class);
	}

	public Flux<UserResponse> getAllUsers() {
		return webClient.get()
				.uri(USER_PATH)
				.retrieve()
				.onStatus(HttpStatusCode::is5xxServerError, CommonExceptionHandler::handle5xxServerError)
				.bodyToFlux(UserResponse.class);
	}

	public Mono<UserResponse> createUser(UserRequest request) {
		return webClient.post()
				.uri(USER_PATH)
				.bodyValue(request)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, CommonExceptionHandler::handle4xxClientError)
				.onStatus(HttpStatusCode::is5xxServerError, CommonExceptionHandler::handle5xxServerError)
				.bodyToMono(UserResponse.class);
	}

	public Mono<Void> deleteUser(Long id) {
		return webClient.delete()
				.uri("/users/{id}", id)
				.retrieve()
				.onStatus(HttpStatus.NOT_FOUND::equals,
						clientResponse -> CommonExceptionHandler.handleNotFound(id))
				.onStatus(HttpStatusCode::is4xxClientError, CommonExceptionHandler::handle4xxClientError)
				.onStatus(HttpStatusCode::is5xxServerError, CommonExceptionHandler::handle5xxServerError)
				.bodyToMono(Void.class);
	}
}
