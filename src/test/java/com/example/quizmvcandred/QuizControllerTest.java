package com.example.quizmvcandred;

import com.example.controller.QuizController;
import com.example.dto.CheckAnswerResult;
import com.example.repository.UserAnswersRepository;
import com.example.service.QuizService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(QuizController.class)
@Import(QuizControllerTest.TestConfig.class)
class QuizControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private QuizService quizService;
	@Autowired
	private UserAnswersRepository userAnswersRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldReturnWelcomeView() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("welcome"));
	}

	@Test
	void shouldReturnOkForCheckEndpoint() throws Exception {
		when(quizService.checkAnswer(0))
				.thenReturn(new CheckAnswerResult(1, true));

		mockMvc.perform(post("/check")
						.param("answerIndex", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.correctIndex").value(1))
				.andExpect(jsonPath("$.selectedCorrect").value(true));
	}

	@Test
	void shouldStartExamAndStoreLoginInSession() throws Exception {
		mockMvc.perform(post("/exam/start")
						.param("login", "testUser"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/exam"))
				.andExpect(MockMvcResultMatchers.request().sessionAttribute("LOGIN", "testUser"));
	}

	@Test
	void shouldInvalidateSessionOnLogout() throws Exception {
		mockMvc.perform(get("/exam/logout")
						.sessionAttr("LOGIN", "testUser"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/exam"));
	}

	@TestConfiguration
	static class TestConfig {

		@Bean
		QuizService quizService() {
			return Mockito.mock(QuizService.class);
		}

		@Bean
		UserAnswersRepository userAnswersRepository() {
			return Mockito.mock(UserAnswersRepository.class);
		}

		@Bean
		ObjectMapper objectMapper() {
			return new ObjectMapper();
		}
	}
}
