package com.defit.entity;

import com.defit.dto.MeasurementRequest.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DailyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();

    private double heightCm;
    private double weightKg;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int experienceYears;
    private long steps;
    private double intakeCalories;
    private double deficit;
    private double bmr;
    private double tdee;
    private double tef;
    private double fatPercent;
    private double leanMass;
    private double bmi;
}
