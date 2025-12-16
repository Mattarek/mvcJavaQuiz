package com.example.dto;

public class UserAnswerDto {
	private Long questionId;
	private String selectedAnswer;
	private boolean correct;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}

	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(final String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(final boolean correct) {
		this.correct = correct;
	}
}
