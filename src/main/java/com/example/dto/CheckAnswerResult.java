package com.example.dto;

public record CheckAnswerResult(
		int correctIndex,
		boolean selectedCorrect
) {}
