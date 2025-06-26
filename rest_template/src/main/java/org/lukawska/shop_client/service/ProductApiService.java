package org.lukawska.shop_client.service;

import org.lukawska.shop_client.dto.ProductApiRequest;
import org.lukawska.shop_client.dto.ProductApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductApiService {

	private final RestTemplate restTemplate;

	@Value("${app.products-url}")
	private String baseUrl;

	public ProductApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ProductApiResponse getProduct(Long id){
		String url = baseUrl + "/{id}";
		return restTemplate.getForObject(url, ProductApiResponse.class, id);
	}

	public ProductApiResponse getProductWithHeaders(Long id , String authToken){
		String url = baseUrl + "/{id}";
		HttpHeaders httpHeaders = new HttpHeaders();

		if (authToken != null && !authToken.isEmpty()) {
			httpHeaders.set("Authorization", authToken);
		}
		httpHeaders.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<ProductApiResponse> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				entity,
				ProductApiResponse.class,
				id
		);

		return response.getBody();
	}

	public ProductApiResponse createProduct(ProductApiRequest request){
		return restTemplate.postForObject(baseUrl, request, ProductApiResponse.class);
	}

	public ProductApiResponse updateProduct(Long id, ProductApiRequest request){
		String url = baseUrl + "/{id}";
		HttpEntity<ProductApiRequest> entity = new HttpEntity<>(request);

		ResponseEntity<ProductApiResponse> response = restTemplate.exchange(
				url,
				HttpMethod.PUT,
				entity,
				ProductApiResponse.class,
				id
		);

		return response.getBody();
	}

	public void deleteProductById(Long id){
		String url = baseUrl + "/{id}";
		restTemplate.delete(url, id);
	}
}
