package org.lukawska.shop_backend.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.UserRequest;
import org.lukawska.shop_backend.dto.UserResponse;
import org.lukawska.shop_backend.entity.User;
import org.lukawska.shop_backend.exception.ExceptionEnum;
import org.lukawska.shop_backend.exception.RestException;
import org.lukawska.shop_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponse getUser(Long id){
		return userRepository.findById(id).map(this::mapToResponse)
				.orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
	}

	private UserResponse mapToResponse(User user){
		return new UserResponse(user.getId(), user.getUsername());
	}

	private User mapToUser(UserRequest userRequest){
		return User.builder()
				.username(userRequest.username())
				.build();
	}
}
