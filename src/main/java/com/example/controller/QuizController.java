package com.example.controller;

import com.example.dto.CheckAnswerResult;
import com.example.dto.UserAnswerDto;
import com.example.entity.UserAnswers;
import com.example.repository.UserAnswersRepository;
import com.example.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Controller
public class QuizController {
	private static final int EXAM_QUESTIONS_COUNT = 20;
	private final QuizService quizService;
	private final UserAnswersRepository userAnswersRepository;

	public QuizController(final QuizService quizService, final UserAnswersRepository userAnswersRepository) {
		this.quizService = quizService;
		this.userAnswersRepository = userAnswersRepository;
	}

	@GetMapping("/")
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/question")
	public String question(final Model model) {
		model.addAttribute("question", quizService.nextQuestion());
		return "question";
	}

	@GetMapping("/exam/logout")
	public String logout(final HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/exam";
	}

	@GetMapping("/allquestions")
	public String allQuestion(final Model model) {
		model.addAttribute("questions", quizService.getQuestions());
		return "allquestions";
	}

	@PostMapping("/exam/finish")
	public ResponseEntity<Void> finishQuiz(
			@RequestBody final List<UserAnswerDto> answers,
			final HttpSession session
	) {

		final String login = (String) session.getAttribute("LOGIN");

		final ObjectMapper mapper = new ObjectMapper();

		final UserAnswers ua = new UserAnswers();
		ua.setLogin(login);
		ua.setAnswers(mapper.writeValueAsString(answers));

		userAnswersRepository.save(ua);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/exam")
	public String exam(final Model model) {
		model.addAttribute("questions", quizService.getCustomValueOfQuestions(EXAM_QUESTIONS_COUNT));
		return "exam";
	}

	@PostMapping("/exam/start")
	public String startQuiz(@RequestParam final String login, final HttpSession httpSession) {
		httpSession.setAttribute("LOGIN", login);
		return "redirect:/exam";
	}

	@PostMapping("/check")
	@ResponseBody
	public CheckAnswerResult check(@RequestParam final int answerIndex) {
		return quizService.checkAnswer(answerIndex);
	}
}
