package com.example.quizmvcandred;

import com.example.connector.QuestionClient;
import com.example.dto.AnswerDto;
import com.example.dto.CheckAnswerResult;
import com.example.dto.QuestionDto;
import com.example.service.QuizService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {
	@Mock
	private QuestionClient questionClient;

	@InjectMocks
	private QuizService quizService;

	@Test
	void shouldReturnTrueWhenCorrectAnswerSelected() {
		// GIVEN
		final AnswerDto a1 = answer("A", false);
		final AnswerDto a2 = answer("B", false);
		final AnswerDto a3 = answer("C", true);

		final QuestionDto question = new QuestionDto();
		question.setAnswers(List.of(a1, a2, a3));

		// new QuestionDto[]{question} - to tworzy tablice 1 elementową z listą question { question }
		when(questionClient.getQuestions())
				.thenReturn(new QuestionDto[]{question});

		// WHEN
		quizService.nextQuestion();
		final CheckAnswerResult result = quizService.checkAnswer(2);

		// THEN
		assertTrue(result.selectedCorrect());
		assertEquals(2, result.correctIndex());
	}

	private AnswerDto answer(final String text, final boolean correct) {
		final AnswerDto dto = new AnswerDto();
		dto.setText(text);
		dto.setCorrect(correct);
		return dto;
	}

	@Test
	void shouldReturnCorrectResultWhenAnswerIsWrong() {
		// GIVEN
		final AnswerDto wrong = new AnswerDto();
		wrong.setText("A");
		wrong.setCorrect(false);

		final AnswerDto correct = new AnswerDto();
		correct.setText("B");
		correct.setCorrect(true);

		final QuestionDto question = new QuestionDto();
		question.setId(1L);
		question.setText("Pytanie testowe");
		question.setAnswers(List.of(wrong, correct));

		when(questionClient.getQuestions())
				.thenReturn(new QuestionDto[]{question});

		quizService.nextQuestion();

		// WHEN
		final CheckAnswerResult result = quizService.checkAnswer(0);

		// THEN
		assertFalse(result.selectedCorrect());
		assertEquals(1, result.correctIndex());
	}

	@Test
	void shouldThrowExceptionWhenNoCurrentQuestion() {
		// WHEN + THEN
		assertThrows(
				IllegalStateException.class,
				() -> quizService.checkAnswer(0)
		);
	}

	@Test
	void shouldReturnUniqueQuestions() {
		// GIVEN
		//		JEŚLI:
		//			ktoś wywoła getQuestions() na tym obiekcie
		//		TO:
		//			zwróć X

		when(questionClient.getQuestions())
				.thenReturn(new QuestionDto[]{
						new QuestionDto(),
						new QuestionDto(),
						new QuestionDto(),
						new QuestionDto()
				});

		// WHEN
		// getCustomValueOfQuestions korzysta z getQuestions, więc z niego zwróci nam to co wyżej
		// zamockowaliśmy
		final List<QuestionDto> result = quizService.getCustomValueOfQuestions(3);

		// THEN
		assertNotNull(result);

		assertEquals(
				result.size(),
				result.stream().distinct().count()
		);
	}
}
