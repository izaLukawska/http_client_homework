package org.lukawska.webclient_demo.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.webclient_demo.client.UserHttpClient;
import org.lukawska.webclient_demo.dto.UserResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HttpClientUserService {

	private final UserHttpClient userHttpClient;

	public Mono<UserResponse> getUserById(Long id){
		return Mono.fromCallable(() -> userHttpClient.getUserById(id));
	}
}