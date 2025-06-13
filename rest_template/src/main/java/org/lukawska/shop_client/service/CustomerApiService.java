package org.lukawska.shop_client.service;

import org.lukawska.shop_client.dto.CustomerApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerApiService {

	private final RestTemplate restTemplate;

	private static final String BASE_URL = "http://localhost:8080";

	public CustomerApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<CustomerApiResponse> getAllCustomers(){
		String url = BASE_URL + "/customers";
		ResponseEntity<List<CustomerApiResponse>> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {
				});

		return response.getBody();
	}
}
