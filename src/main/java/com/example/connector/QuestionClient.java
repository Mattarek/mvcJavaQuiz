package com.example.connector;

import com.example.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuestionClient {
	private final RestTemplate restTemplate;
	private final String url;

	public QuestionClient(final RestTemplate restTemplate, @Value("${question.api.url}") final String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public QuestionDto[] getQuestions() {
		return restTemplate.getForObject(url, QuestionDto[].class);
	}
}
