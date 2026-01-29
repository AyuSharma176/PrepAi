package com.example.prepai.service;

import com.example.prepai.ai.GeminiRequest;
import com.example.prepai.ai.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiEvaluationService {

    @Value("${gemini.api.key}")
    private String apiKey;

    // UPDATED: 'gemini-1.5-flash' is deprecated.
    // Switched to 'gemini-2.5-flash' (Current standard as of 2026).
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    private final RestTemplate restTemplate = new RestTemplate();

    public String evaluate(String question, String answer) {

        try {
            String prompt = """
You are a VERY STRICT technical interviewer at a top-tier product company.

Question:
%s

Candidate Answer:
%s

Scoring Rubric:
- 9–10: Covers internal memory layout, JVM heap allocation, object headers, bounds checking, and performance implications
- 7–8: Correct internal explanation but missing JVM-level or optimization details
- 5–6: Basic explanation of contiguous memory and indexing only
- 3–4: Partial or shallow understanding
- 0–2: Incorrect or vague answer

Rules:
- Do NOT be generous
- Do NOT praise unless score >= 8
- Penalize missing concepts strictly
- 10/10 should be RARE

Respond in EXACT format:
SCORE: X
FEEDBACK: One sentence justification
""".formatted(question, answer);


            GeminiRequest request = new GeminiRequest();
            request.setContents(List.of(
                    Map.of("parts", List.of(
                            Map.of("text", prompt)
                    ))
            ));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GeminiRequest> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<GeminiResponse> response =
                    restTemplate.postForEntity(
                            GEMINI_API_URL + apiKey,
                            entity,
                            GeminiResponse.class
                    );

            if (response.getBody() == null ||
                    response.getBody().getCandidates() == null ||
                    response.getBody().getCandidates().isEmpty()) {

                return defaultFeedback();
            }

            return response.getBody()
                    .getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();

        } catch (Exception e) {
            System.err.println("AI Evaluation Error: " + e.getMessage());
            e.printStackTrace();
            return defaultFeedback();
        }
    }

    private String defaultFeedback() {
        return "AI evaluation unavailable. Showing rule-based feedback.";
    }
}