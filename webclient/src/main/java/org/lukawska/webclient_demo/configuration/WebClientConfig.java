package org.lukawska.webclient_demo.configuration;

import org.lukawska.webclient_demo.client.UserHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient(){
		return WebClient.builder()
				.baseUrl("http://localhost:8080")
				.filter(logRequest())
				.filter(logResponse())
				.filter(authFilter())
				.build();
	}

	@Bean
	public UserHttpClient userHttpClient(WebClient webClient) {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClient))
				.build();

		return factory.createClient(UserHttpClient.class);
	}


	private ExchangeFilterFunction logRequest(){
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
			clientRequest.headers().forEach((name, values) ->
					values.forEach(value -> System.out.println(name + ": " + value)));
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			System.out.println("Response Status: " + clientResponse.statusCode());
			return Mono.just(clientResponse);
		});
	}

	private ExchangeFilterFunction authFilter(){
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->
				Mono.just(ClientRequest.from(clientRequest)
						.header("Authorization", "Bearer" + getToken())
						.build()));
	}

	private String getToken() {
		return "Example token";
	}
}
