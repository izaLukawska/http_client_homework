package org.lukawska.restclient.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.restclient.dto.UserRequest;
import org.lukawska.restclient.dto.UserResponse;
import org.lukawska.restclient.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(userService.createUser(userRequest));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}
}
