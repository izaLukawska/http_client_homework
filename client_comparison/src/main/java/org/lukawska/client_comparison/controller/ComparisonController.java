package org.lukawska.client_comparison.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.client_comparison.service.ComparisonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ComparisonController {

	private final ComparisonService service;

	@GetMapping("/rest-template")
	public String testRestTemplate() {
		return service.useRestTemplate();
	}

	@GetMapping("/web-client")
	public Mono<String> testWebClient() {
		return service.useWebClient();
	}

	@GetMapping("/rest-client")
	public String testRestClient() {
		return service.useRestClient();
	}

	@GetMapping("/info")
	public String info() {
		return "Test for /info";
	}
}
