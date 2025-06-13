package org.lukawska.client_comparison.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ComparisonService {

	private final RestTemplate restTemplate;

	private final WebClient webClient;

	private final RestClient restClient;

	private static final String BASE_URL = "http://localhost:8080";


	public String useRestTemplate() {
		return restTemplate.getForObject(BASE_URL + "/info", String.class);
	}

	public Mono<String> useWebClient() {
		return webClient.get()
				.uri("/info")
				.retrieve()
				.bodyToMono(String.class);
	}

	public String useRestClient() {
		return restClient.get()
				.uri("/info")
				.retrieve()
				.body(String.class);
	}
}