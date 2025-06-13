package org.lukawska.shop_backend.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.UserResponse;
import org.lukawska.shop_backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@GetMapping({"/{id}"})
	public UserResponse getOrder(@PathVariable Long id){
		return userService.getUser(id);
	}

	@GetMapping("/simulate_5xx")
	public UserResponse simulateServerError() {
		throw new RuntimeException("Simulated Internal Server Error");
	}
}
