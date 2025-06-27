package org.lukawska.restclient.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.rmi.ServerException;

public class RestClientErrorHandler {
	public static RestClient.ResponseSpec applyCommonErrorHandling(RestClient.ResponseSpec spec) {
		return spec
				.onStatus(HttpStatusCode::is4xxClientError, (
						(request, response) -> {
							throw new ClientException("Client error" + response.getStatusCode());
						}))
				.onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
					throw new ServerException("Server error" + response.getStatusCode());
				}));
	}
}
