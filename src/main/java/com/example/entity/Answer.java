package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	private boolean correct;

	@ManyToOne
	private Question question;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(final boolean correct) {
		this.correct = correct;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(final Question question) {
		this.question = question;
	}
}
