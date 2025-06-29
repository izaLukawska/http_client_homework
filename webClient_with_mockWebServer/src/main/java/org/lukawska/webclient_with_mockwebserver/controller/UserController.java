package org.lukawska.webclient_with_mockwebserver.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.webclient_with_mockwebserver.dto.UserRequest;
import org.lukawska.webclient_with_mockwebserver.dto.UserResponse;
import org.lukawska.webclient_with_mockwebserver.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/{id}")
	public Mono<UserResponse> getUserById(@PathVariable Long id){
		return userService.getUserById(id);
	}

	@GetMapping
	public Flux<UserResponse> getAllUsers(){
		return userService.getAllUsers();
	}

	@PostMapping
	public Mono<UserResponse> createUser(@RequestBody UserRequest userRequest){
		return userService.createUser(userRequest);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteUserById(@PathVariable Long id){
		return userService.deleteUser(id);
	}
}
