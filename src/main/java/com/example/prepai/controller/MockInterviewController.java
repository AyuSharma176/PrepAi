package com.example.prepai.controller;

import com.example.prepai.model.InterviewSession;
import com.example.prepai.service.MockInterviewService;
import org.springframework.web.bind.annotation.*;
import com.example.prepai.dto.MockAnswerRequest;

@RestController
@RequestMapping("/api/mock")
@CrossOrigin("*")
public class MockInterviewController {

    private final MockInterviewService service;

    public MockInterviewController(MockInterviewService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public InterviewSession start(@RequestParam String topic) {
        return service.start(topic);
    }

    @GetMapping("/next-question")
    public String next(@RequestParam Long sessionId) {
        return service.nextQuestion(sessionId);
    }
    @PostMapping("/answer")
    public String submitAnswer(@RequestBody MockAnswerRequest request) {
        return service.submitAnswer(request.getSessionId(), request.getAnswer());
    }
}
