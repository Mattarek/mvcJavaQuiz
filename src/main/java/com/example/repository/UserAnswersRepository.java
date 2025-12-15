package com.example.repository;

import com.example.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswersRepository extends JpaRepository<UserAnswers, Long> {
}
