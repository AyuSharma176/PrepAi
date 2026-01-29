package com.example.prepai.controller;

import com.example.prepai.dto.InterviewRequest;
import com.example.prepai.model.InterviewAttempt;
import com.example.prepai.service.InterviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    // ✅ Constructor Injection (BEST PRACTICE)
    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/question")
    public String getQuestion(
            @RequestParam String topic,
            @RequestParam(defaultValue = "easy") String difficulty
    ) {
        return "Explain Arrays in Java (Difficulty: " + difficulty + ")";
    }

    @PostMapping("/submit")
    public InterviewAttempt submitAnswer(
            @RequestBody InterviewRequest request
    ) {
        return interviewService.saveAttempt(
                request.getTopic(),
                request.getDifficulty(),
                request.getQuestion(),
                request.getAnswer()
        );
    }
}
