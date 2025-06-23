package org.lukawska.shop_client.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InvoiceApiService {

	private final RestTemplate restTemplate;

	@Value("${app.invoice-upload-url}")
	private String uploadUrl;

	public InvoiceApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String uploadFile(MultipartFile file) {
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("metadata", "file-metadata");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, Object>> entity =
				new HttpEntity<>(body, headers);

		return restTemplate.postForObject(uploadUrl, entity, String.class);
	}
}
