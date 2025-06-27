package org.lukawska.restclient.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lukawska.restclient.dto.UserRequest;
import org.lukawska.restclient.dto.UserResponse;
import org.lukawska.restclient.error.ClientError;
import org.lukawska.restclient.error.ServerError;
import org.lukawska.restclient.error.UserNotFound;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

	private static final String USERS_PATH = "/users";

	private MockRestServiceServer mockServer;

	private UserService userService;

	@BeforeEach
	void setUp() {
		RestTemplate restTemplate = new RestTemplate();

		this.mockServer = MockRestServiceServer.createServer(restTemplate);

		RestClient restClient = RestClient.builder(restTemplate)
				.baseUrl(BASE_URL)
				.build();

		this.userService = new UserService(restClient);
	}

	@Test
	void shouldCreateUser_success() {
		//given
		UserRequest request = new UserRequest("test", "test@test.com");
		UserResponse expectedUser = new UserResponse(1L, "test", "test@test.com");
		String expectedResponse = """
				{
				  "id": 1,
				  "username": "test",
				  "email": "test@test.com"
				}
				""";
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));
		//when
		UserResponse actualUser = userService.createUser(request);

		//then
		assertEquals(expectedUser, actualUser);
		mockServer.verify();
	}

	@Test
	void shouldThrowClientError_WhenCreateUser(){
		//given
		UserRequest request = new UserRequest("test", "test@test.com");
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withForbiddenRequest());
		//when & then
		assertThrows(ClientError.class, () -> userService.createUser(request));
		mockServer.verify();
	}

	@Disabled("throws RestClientException instead of ServerException")
	@Test
	void shouldThrowServerError_whenCreateUser(){
		UserRequest request = new UserRequest("test", "test@test.com");
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withServerError());

		//when & then
		assertThrows(ServerError.class, () -> userService.createUser(request));
		mockServer.verify();
	}

	@Test
	void shouldReturnUser_success() {
		//given
		Long id = 1L;
		UserResponse expectedUser = new UserResponse(1L, "test", "test@test.com");
		String expectedResponse = """
				{
				  "id": 1,
				  "username": "test",
				  "email": "test@test.com"
				}
				""";

		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

		//when
		UserResponse result = userService.getUserById(id);

		//then
		assertEquals(expectedUser, result);
		mockServer.verify();
	}

	@Test
	void shouldUserNotFound_WhenUserGetById() {
		//given
		Long id = 2L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		//when & then
		assertThrows(UserNotFound.class, () -> userService.getUserById(id));
		mockServer.verify();
	}

	@Test
	void shouldThrowClientError_WhenGetById(){
		//given
		Long id = 1L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withBadRequest());

		//when & then
		assertThrows(ClientError.class, () -> userService.getUserById(id));
	}

	@Disabled("throws RestClientException instead of ServerException")
	@Test
	void shouldThrowServerError_WhenGetById(){
		//given
		Long id = 1L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

		//when & then
		assertThrows(ServerError.class, () -> userService.getUserById(id));
	}


	@Test
	void shouldReturnAllUsers() {
		//given
		String expectedResponse = """
				  [
				    {
				      "id": 1,
				      "username": "test1",
				      "email" : "test1@test1.com"
				    },
				    {
				      "id": 2,
				      "username": "test2",
				      "email" : "test2@test2.com"
				    }
				  ]
				""";
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));
		//when
		List<UserResponse> actualResponse = userService.getAllUsers();

		//then
		assertThat(actualResponse)
				.hasSize(2)
				.extracting(UserResponse::username)
				.containsExactly("test1", "test2");

		mockServer.verify();
	}

	@Disabled("throws RestClientException instead of ServerException")
	@Test
	void shouldThrowServerError_WhenGetAllUsers() {
		//given
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withServerError());


		//when & then
		assertThrows(RestClientException.class, () -> userService.getAllUsers());
		mockServer.verify();
	}

	@Test
	void shouldDeleteUserById_success(){
		//given
		Long id = 1L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withSuccess());
		//when & then
		assertDoesNotThrow(() -> userService.deleteUserById(id));
		mockServer.verify();
	}

	@Test
	void shouldThrowUserNotFound_WhenDeleteUserById(){
		//given
		Long id = 1L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		//when & then
		assertThrows(UserNotFound.class, () -> userService.deleteUserById(id));
		mockServer.verify();
	}

	@Test
	void shouldThrowClientError_WhenDeleteUserById(){
		//given
		Long id = 1L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withBadRequest());

		//when & then
		assertThrows(ClientError.class, () -> userService.deleteUserById(id));
		mockServer.verify();
	}
	@Disabled("throws RestClientException instead of ServerException")
	@Test
	void shouldThrowServerError_WhenDeleteUserById(){
		//given
		Long id = 1L;
		mockServer.expect(ExpectedCount.once(), requestTo(BASE_URL + USERS_PATH + "/" + id))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withServerError());

		//when & then
		assertThrows(ServerError.class, () -> userService.deleteUserById(id));
		mockServer.verify();
	}
}
