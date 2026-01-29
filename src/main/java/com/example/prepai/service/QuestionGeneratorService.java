package com.example.prepai.service;

import org.springframework.stereotype.Service;

@Service
public class QuestionGeneratorService {

    public String generate(String topic, int round) {
        return switch (round) {
            case 1 -> "What is " + topic + " and why is it used?";
            case 2 -> "Explain the internal working of " + topic + ".";
            case 3 -> "What are the common pitfalls or limitations of " + topic + "?";
            case 4 -> "Discuss the time and space complexity considerations for " + topic + ".";
            case 5 -> "Describe a real-world scenario where you would implement " + topic + ".";
            default -> "Interview Completed.";
        };
    }
}