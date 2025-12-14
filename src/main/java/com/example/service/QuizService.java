package com.example.service;

import com.example.connector.QuestionClient;
import com.example.dto.AnswerDto;
import com.example.dto.CheckAnswerResult;
import com.example.dto.QuestionDto;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class QuizService {

	private static final String QUESTIONS_URL =
			"https://public.andret.eu/questions.json";

	private final RestTemplate restTemplate;
	private final QuestionClient questionClient;

	// cache pytań
	private List<QuestionDto> questions;

	// aktualne pytanie
	private QuestionDto currentQuestion;

	public QuizService(final RestTemplate restTemplate, final QuestionClient questionClient) {
		this.restTemplate = restTemplate;
		this.questionClient = questionClient;
	}

	public QuestionDto getQuestion() {
		if (currentQuestion == null) {
			currentQuestion = getRandomQuestion();
		}
		return currentQuestion;
	}

	public QuestionDto nextQuestion() {
		currentQuestion = getRandomQuestion();
		return currentQuestion;
	}

	public CheckAnswerResult checkAnswer(final int selectedIndex) {

		if (currentQuestion == null) {
			throw new IllegalStateException("No active question");
		}

		final List<AnswerDto> answers = currentQuestion.getAnswers();

		int correctIndex = -1;

		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i).isCorrect()) {
				correctIndex = i;
				break;
			}
		}

		final boolean selectedCorrect = selectedIndex == correctIndex;

		return new CheckAnswerResult(correctIndex, selectedCorrect);
	}

	public List<QuestionDto> getQuestions() {
		return List.of(questionClient.getQuestions(QUESTIONS_URL));
	}

	private QuestionDto getRandomQuestion() {
		final List<QuestionDto> all = getQuestions();
		return all.get(new Random().nextInt(all.size()));
	}

	public @Nullable List<QuestionDto> getCustomValueOfQuestions(final int count) {
		final List<QuestionDto> all = getQuestions();
		final Random random = new Random();
		final Set<Integer> indexes = new HashSet<>();

		while (indexes.size() < Math.min(count, all.size())) {
			indexes.add(random.nextInt(all.size()));
		}

		return indexes.stream()
				.map(all::get)
				.toList();
	}
}
