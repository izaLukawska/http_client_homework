package org.lukawska.webclient_demo.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.webclient_demo.dto.UserResponse;
import org.lukawska.webclient_demo.service.ReactiveUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api_users")
@RequiredArgsConstructor
public class UserController {

	private final ReactiveUserService reactiveUserService;

	@GetMapping("/{id}")
	public Mono<UserResponse> getUser(@PathVariable Long id) {
		return reactiveUserService.getUserWithErrorHandling(id);
	}

	@GetMapping("/simulate_5xx")
	public Mono<UserResponse> getUserError() {
		throw new RuntimeException("Simulated Internal Server Error");
	}
}
