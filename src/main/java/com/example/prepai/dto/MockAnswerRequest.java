package com.example.prepai.dto;

import lombok.Data;

@Data
public class MockAnswerRequest {
    private Long sessionId;
    private String answer;
}