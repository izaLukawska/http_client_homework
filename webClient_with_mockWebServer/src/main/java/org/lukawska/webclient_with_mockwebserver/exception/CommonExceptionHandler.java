package org.lukawska.webclient_with_mockwebserver.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonExceptionHandler {
	public static Mono<? extends Throwable> handle4xxClientError(ClientResponse clientResponse) {
		return clientResponse.bodyToMono(String.class).map(ClientException::new);
	}

	public static Mono<? extends Throwable> handle5xxServerError(ClientResponse clientResponse) {
		return clientResponse.bodyToMono(String.class).map(ServerException::new);
	}

	public static Mono<? extends Throwable> handleNotFound(Long id) {
		return Mono.error(new ResourceNotFoundException(id));
	}
}
