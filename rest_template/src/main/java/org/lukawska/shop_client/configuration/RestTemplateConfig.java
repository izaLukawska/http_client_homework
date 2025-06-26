package org.lukawska.shop_client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public RestTemplate customRestTemplate(){
		HttpComponentsClientHttpRequestFactory factory =
				new HttpComponentsClientHttpRequestFactory();

		factory.setConnectTimeout(3000);
		factory.setReadTimeout(5000);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(factory);
		return restTemplate;
	}
}
