package com.example.prepai.dto;

import lombok.Data;

@Data
public class InterviewRequest {
    private String topic;
    private String difficulty;
    private String question;
    private String answer;
}
