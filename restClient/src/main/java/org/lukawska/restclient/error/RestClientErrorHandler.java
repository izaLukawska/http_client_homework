package org.lukawska.restclient.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.rmi.ServerException;

public class RestClientErrorHandler {
	public static RestClient.ResponseSpec applyCommonErrorHandling(RestClient.ResponseSpec spec) {
		return spec
				.onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
							throw new ClientError("Client error" + response.getStatusCode());
						}))
				.onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
							throw new ServerException("Server error" + response.getStatusCode());
						}));
	}

	public static RestClient.ResponseSpec apply5xxOnlyHandling(RestClient.ResponseSpec spec) {
		return spec.onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
					throw new ServerException("Server error" + response.getStatusCode());
				}));
	}
}
