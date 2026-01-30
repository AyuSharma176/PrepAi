# PrepAI – AI-Powered Mock Interview Platform

PrepAI is a backend-driven mock interview platform that simulates real technical interviews using AI-based evaluation.

## 🚀 Features
- Topic-based mock interview sessions
- Multi-round interview flow
- AI-driven answer evaluation using Google Gemini API
- Strict scoring rubric with realistic interview grading
- Stateful interview sessions with score tracking
- PostgreSQL persistence using Spring Data JPA
- Graceful fallback evaluation when AI is unavailable

## 🧠 Tech Stack
- Java 23
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Google Gemini API
- RESTful APIs

## 📌 API Endpoints

### Start Interview
POST /api/mock/start?topic=arrays

### Get Next Question
GET /api/mock/next-question?sessionId=1


### Submit Answer

POST /api/mock/answer

```json
{
  "sessionId": 1,
  "answer": "Your answer here"
}

