package com.example.prepai.service;

import com.example.prepai.model.InterviewAttempt;
import com.example.prepai.repository.InterviewAttemptRepository;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    private final InterviewAttemptRepository repository;
    private final AiEvaluationService aiEvaluationService;

    // ✅ Constructor Injection
    public InterviewService(InterviewAttemptRepository repository,
                            AiEvaluationService aiEvaluationService) {
        this.repository = repository;
        this.aiEvaluationService = aiEvaluationService;
    }

    public InterviewAttempt saveAttempt(String topic, String difficulty,
                                        String question, String answer) {

        // 🔥 Call AI here
        String aiFeedback = aiEvaluationService.evaluate(question, answer);

        // (Optional simple score extraction for now)
        int score = extractScore(aiFeedback);

        InterviewAttempt attempt = new InterviewAttempt();
        attempt.setTopic(topic);
        attempt.setDifficulty(difficulty);
        attempt.setQuestion(question);
        attempt.setAnswer(answer);
        attempt.setScore(score);
        attempt.setFeedback(aiFeedback);

        return repository.save(attempt);
    }

    // 🧠 Simple score extractor (temporary)
    private int extractScore(String feedback) {
        try {
            if (feedback.contains("Score")) {
                String num = feedback.replaceAll("[^0-9]", "");
                return Integer.parseInt(num.substring(0, 1));
            }
        } catch (Exception ignored) {}
        return 5; // default
    }
}
