package com.example.prepai.repository;

import com.example.prepai.model.InterviewAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewAttemptRepository
        extends JpaRepository<InterviewAttempt, Long> {
}
