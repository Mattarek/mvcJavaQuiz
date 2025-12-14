package com.example.connector;

import com.example.dto.QuestionDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuestionClient {
	private final RestTemplate restTemplate;

	public QuestionClient(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public QuestionDto[] getQuestions(final String url) {
		return restTemplate.getForObject(url, QuestionDto[].class);
	}
}
