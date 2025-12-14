package com.example.dto;

public class AnswerDto {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	private String text;
	private boolean correct;
	private String explanation;

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(final boolean correct) {
		this.correct = correct;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	public record CheckAnswerResult(
			int correctIndex,
			boolean selectedCorrect
	) {}

}
