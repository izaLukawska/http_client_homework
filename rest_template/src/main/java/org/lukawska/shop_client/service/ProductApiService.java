package org.lukawska.shop_client.service;

import org.lukawska.shop_client.dto.ProductApiRequest;
import org.lukawska.shop_client.dto.ProductApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductApiService {

	private final RestTemplate restTemplate;

	private static final String BASE_URL = "http://localhost:8080";

	public ProductApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ProductApiResponse getProduct(Long id){
		String url = BASE_URL + "/products/{id}";
		return restTemplate.getForObject(url, ProductApiResponse.class, id);
	}

	public ProductApiResponse getProductWithHeaders(Long id , String authToken){
		String url = BASE_URL + "/products/{id}";
		HttpHeaders httpHeaders = new HttpHeaders();

		if (authToken != null && !authToken.isEmpty()) {
			httpHeaders.set("Authorization", authToken);
		}
		httpHeaders.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<ProductApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductApiResponse.class, id);

		return response.getBody();
	}

	public ProductApiResponse createProduct(ProductApiRequest request){
		String url = BASE_URL + "/products";
		return restTemplate.postForObject(url, request, ProductApiResponse.class);
	}

	public ProductApiResponse updateProduct(Long id, ProductApiRequest request){
		String url = BASE_URL + "/products/{id}";
		HttpEntity<ProductApiRequest> entity = new HttpEntity<>(request);

		ResponseEntity<ProductApiResponse> response = restTemplate.exchange(url, HttpMethod.PUT, entity, ProductApiResponse.class, id);

		return response.getBody();
	}

	public void deleteProductById(Long id){
		String url = BASE_URL + "/products/{id}";
		restTemplate.delete(url, id);
	}
}
