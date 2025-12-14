package com.example.controller;

import com.example.dto.CheckAnswerResult;
import com.example.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuizController {
	private static final int examQuestionCount = 20;
	private final QuizService quizService;

	public QuizController(final QuizService quizService) {
		this.quizService = quizService;
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

	@GetMapping("/allquestions")
	public String allQuestion(final Model model) {
		model.addAttribute("questions", quizService.getQuestions());
		return "allquestions";
	}

	@GetMapping("/exam")
	public String twentyQuestion(final Model model) {
		model.addAttribute("questions", quizService.getCustomValueOfQuestions(examQuestionCount));
		return "exam";
	}

	@PostMapping("/check")
	@ResponseBody
	public CheckAnswerResult check(@RequestParam final int answerIndex) {
		return quizService.checkAnswer(answerIndex);
	}
}
