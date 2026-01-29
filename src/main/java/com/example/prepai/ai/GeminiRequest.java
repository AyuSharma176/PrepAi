package com.example.prepai.ai;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GeminiRequest {
    private List<Map<String, Object>> contents;
}
