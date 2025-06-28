package org.lukawska.webclient_with_mockwebserver.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lukawska.webclient_with_mockwebserver.exception.ClientException;
import org.lukawska.webclient_with_mockwebserver.exception.ResourceNotFoundException;
import org.lukawska.webclient_with_mockwebserver.exception.ServerException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private MockWebServer mockWebServer;

	private UserService userService;

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
		String responseBody = """
				{
				    "id": 1,
				    "username": "test",
				    "email": "test@test.com"
				}
				""";

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
		try {
			RecordedRequest request = mockWebServer.takeRequest();
			assertThat(request.getPath()).isEqualTo("/users/1");
			assertThat(request.getMethod()).isEqualTo("GET");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("Test interrupted");
		}
	}

	@Test
	void shouldThrowResourceNotFound_WhenGetUserById() {
		//given
		Long id = 1L;
		mockWebServer.enqueue(new MockResponse().setResponseCode(404));

		//when
		StepVerifier.create(userService.getUserById(id))
				//then
				.expectError(ResourceNotFoundException.class)
				.verify();

		try {
			RecordedRequest recordedRequest = mockWebServer.takeRequest();
			assertThat(recordedRequest.getPath()).isEqualTo("/users/" + id);
			assertThat(recordedRequest.getMethod()).isEqualTo("GET");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("Test interrupted while waiting for request");
		}
	}

	@Test
	void shouldThrowClientException_WhenGetUserById() {
		//given
		Long id = 1L;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when
		StepVerifier.create(userService.getUserById(id))
				//then
				.expectError(ClientException.class)
				.verify();

		try {
			RecordedRequest recordedRequest = mockWebServer.takeRequest();
			assertThat(recordedRequest.getPath()).isEqualTo("/users/" + id);
			assertThat(recordedRequest.getMethod()).isEqualTo("GET");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("Test interrupted while waiting for request");
		}
	}

	@Test
	void shouldThrowServerException_WhenGetUserById() {
		//given
		Long id = 1L;
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		mockWebServer.enqueue(new MockResponse()
				.setResponseCode(status.value())
				.setBody(status.getReasonPhrase()));

		//when
		StepVerifier.create(userService.getUserById(id))
				//then
				.expectError(ServerException.class)
				.verify();

		try {
			RecordedRequest recordedRequest = mockWebServer.takeRequest();
			assertThat(recordedRequest.getPath()).isEqualTo("/users/" + id);
			assertThat(recordedRequest.getMethod()).isEqualTo("GET");

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("Test interrupted while waiting for request");
		}
	}
}
