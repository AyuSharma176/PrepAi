package com.example.prepai.ai;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class OpenAiRequest {
    private String model;
    private List<Map<String, String>> messages;
    private double temperature;
}
