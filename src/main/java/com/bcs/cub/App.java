package com.bcs.cub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {

	@Bean("restTemplate")
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {

		RestTemplate restTemplate = builder
				.build();
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
