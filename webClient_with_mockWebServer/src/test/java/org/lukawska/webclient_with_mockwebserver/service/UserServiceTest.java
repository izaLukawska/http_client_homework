package org.lukawska.webclient_with_mockwebserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lukawska.webclient_with_mockwebserver.dto.UserRequest;
import org.lukawska.webclient_with_mockwebserver.dto.UserResponse;
import org.lukawska.webclient_with_mockwebserver.exception.ClientException;
import org.lukawska.webclient_with_mockwebserver.exception.ResourceNotFoundException;
import org.lukawska.webclient_with_mockwebserver.exception.ServerException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private MockWebServer mockWebServer;

	private UserService userService;

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpObjectMapper() {
		objectMapper = new ObjectMapper();
	}

	@BeforeEach
	void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();

		String baseUrl = mockWebServer.url("/").toString();
		userService = new UserService(WebClient.builder()
				.baseUrl(baseUrl)
				.build());
	}

	@AfterEach
	void tearDown() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	void shouldGetUserById() {
		//given
		Long id = 1L;
		UserResponse expectedResponse = new UserResponse(1L, "test", "test@test.com");
		String responseBody;
		try {
			responseBody = objectMapper.writeValueAsString(expectedResponse);
		} catch (JsonProcessingException e) {
			fail("Failed to serialize UserResponse to JSON: " + e.getMessage());
			return;
		}

		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", "application/json")
				.setBody(responseBody));

		//when
		StepVerifier.create(userService.getUserById(id))
				// Then
				.expectNextMatches(user -> user.id().equals(1L) &&
						user.username().equals("test"))
				.verifyComplete();
	}

	@Test
	void shouldThrowResourceNotFound_WhenGetUserById() {
		//given
		Long id = 1L;
		mockWebServer.enqueue(new MockResponse().setResponseCode(404));

		//when && then
		StepVerifier.create(userService.getUserById(id))
				.expectError(ResourceNotFoundException.class)
				.verify();
	}

	@Test
	void shouldThrowClientException_WhenGetUserById() {
		//given
		Long id = 1L;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when && then
		StepVerifier.create(userService.getUserById(id))
				.expectError(ClientException.class)
				.verify();
	}

	@Test
	void shouldThrowServerException_WhenGetUserById() {
		//given
		Long id = 1L;
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when && then
		StepVerifier.create(userService.getUserById(id))
				.expectError(ServerException.class)
				.verify();
	}

	@Test
	void shouldReturnAllUsers_success() {
		//given
		List<UserResponse> expectedUserResponses = List.of(
				new UserResponse(1L, "user1", "user1@example.com"),
				new UserResponse(2L, "user2", "user2@example.com")
		);
		String responseBody = "";
		try {
			responseBody = objectMapper.writeValueAsString(expectedUserResponses);
		} catch (JsonProcessingException e) {
			fail("Failed to serialize List<UserResponse> to JSON: " + e.getMessage());
		}
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", "application/json")
				.setBody(responseBody));

		//when && then
		StepVerifier.create(userService.getAllUsers())
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	void shouldThrowServerException_WhenGetAllUsers() {
		//given
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when && then
		StepVerifier.create(userService.getAllUsers())
				.expectError(ServerException.class)
				.verify();

	}

	@Test
	void shouldCreateUser_success() {
		//given
		UserRequest request = new UserRequest("test1", "test@test.com");
		UserResponse expectedResponse = new UserResponse(1L, "test1", "test@test.com");
		String responseBody;
		try {
			responseBody = objectMapper.writeValueAsString(expectedResponse);
		} catch (JsonProcessingException e) {
			fail("Failed to serialize UserResponse to JSON: " + e.getMessage());
			return;
		}
		mockWebServer.enqueue(new MockResponse()
				.setHeader("Content-Type", "application/json")
				.setResponseCode(201)
				.setBody(responseBody));

		//when && then
		StepVerifier.create(userService.createUser(request))
				.expectNextMatches(u -> u.username().equals("test1"))
				.verifyComplete();
	}

	@Test
	void shouldThrowClientException_WhenCreateUser() {
		//given
		UserRequest request = new UserRequest("test1", "test@test.com");
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when && then
		StepVerifier.create(userService.createUser(request))
				.expectError(ClientException.class)
				.verify();
	}

	@Test
	void shouldThrowServerException_WhenCreateUser() {
		//given
		UserRequest request = new UserRequest("test1", "test@test.com");
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when && then
		StepVerifier.create(userService.createUser(request))
				.expectError(ServerException.class)
				.verify();

	}

	@Test
	void shouldDeleteUserById_success_withVerify(){
		//given
		Long id = 1L;
		HttpStatus status = HttpStatus.NO_CONTENT;
		mockWebServer.enqueue(new MockResponse().setResponseCode(status.value()));

		//when && then
		StepVerifier.create(userService.deleteUser(id))
				.verifyComplete();

		try {
			RecordedRequest recordedRequest = mockWebServer.takeRequest();
			assertThat(recordedRequest.getPath()).isEqualTo("/users/1");
			assertThat(recordedRequest.getMethod()).isEqualTo("DELETE");
		} catch (InterruptedException e){
			Thread.currentThread().interrupt();
			fail("Test interrupted");
		}
	}
	@Test
	void shouldThrowResourceNotFoundException_WhenDeleteUserById(){
		//given
		Long id = 999L;
		HttpStatus status = HttpStatus.NOT_FOUND;
		mockWebServer.enqueue(new MockResponse().setResponseCode(status.value()));

		//when
		StepVerifier.create(userService.deleteUser(id))
				.expectError(ResourceNotFoundException.class)
				.verify();
	}

	@Test
	void shouldThrowClientException_WhenDeleteUserById(){
		//given
		Long id = 999L;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when
		StepVerifier.create(userService.deleteUser(id))
				.expectError(ClientException.class).
				verify();
	}
	@Test
	void shouldThrowServerException_WhenDeleteUserById(){
		//given
		Long id = 999L;
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when
		StepVerifier.create(userService.deleteUser(id))
				.expectError(ServerException.class)
				.verify();

	}
}
