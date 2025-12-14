package com.example.dto;

import java.util.List;

public class QuestionDto {
	private Long id;
	private String advancement;
	private String category;
	private String text;
	private List<AnswerDto> answers;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getAdvancement() {
		return advancement;
	}

	public void setAdvancement(final String advancement) {
		this.advancement = advancement;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public List<AnswerDto> getAnswers() {
		return answers;
	}

	public void setAnswers(final List<AnswerDto> answers) {
		this.answers = answers;
	}
}
