package com.example.prepai.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interview_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String difficulty;

    @Column(length = 2000)
    private String question;

    @Column(length = 4000)
    private String answer;

    @Column(length = 4000)
    private String feedback;

    private int score;
}
