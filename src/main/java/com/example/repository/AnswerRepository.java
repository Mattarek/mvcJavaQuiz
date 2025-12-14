package com.example.repository;

import com.example.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	boolean existsByIdAndCorrectTrue(final Long id);
}
