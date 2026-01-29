package com.example.prepai.service;

import com.example.prepai.model.InterviewSession;
import com.example.prepai.repository.InterviewSessionRepository;
import org.springframework.stereotype.Service;
import com.example.prepai.service.AiEvaluationService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MockInterviewService {

    private final InterviewSessionRepository sessionRepo;
    private final QuestionGeneratorService questionService;
    private final AiEvaluationService aiEvaluationService;
    public MockInterviewService(InterviewSessionRepository sessionRepo,
                                QuestionGeneratorService questionService, AiEvaluationService aiEvaluationService) {
        this.sessionRepo = sessionRepo;
        this.questionService = questionService;
        this.aiEvaluationService = aiEvaluationService;
    }

    public InterviewSession start(String topic) {
        InterviewSession session = new InterviewSession();
        session.setTopic(topic);
        session.setCurrentQuestion(1);
        session.setTotalScore(0);
        session.setCompleted(false);
        return sessionRepo.save(session);
    }

    public String nextQuestion(Long sessionId) {
        InterviewSession session = sessionRepo.findById(sessionId).orElseThrow();
        return questionService.generate(
                session.getTopic(),
                session.getCurrentQuestion()
        );
    }
    public String submitAnswer(Long sessionId, String answer) {

        InterviewSession session = sessionRepo.findById(sessionId).orElseThrow();

        String question = questionService.generate(
                session.getTopic(),
                session.getCurrentQuestion()
        );

        String feedback = aiEvaluationService.evaluate(question, answer);
        int score = extractScore(feedback);

        session.setTotalScore(session.getTotalScore() + score);
        sessionRepo.save(session);

        return feedback; // DO NOT append score again

    }

    private int extractScore(String feedback) {
        try {
            Pattern pattern = Pattern.compile("SCORE:\\s*(\\d+)");
            Matcher matcher = pattern.matcher(feedback);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception ignored) {}
        return 5; // fallback
    }
}
