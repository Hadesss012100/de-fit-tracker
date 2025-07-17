package com.defit.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class MeasurementRequest {
    @Positive
    private double heightCm;
    @Positive
    private double weightKg;
    @Min(10)
    @Max(120)
    private int age;
    @NotNull
    private Gender gender;
    @Min(0)
    private int experienceYears;
    @NotEmpty
    private List<FoodItemDto> foodIntake;
    @Min(0)
    private long steps;
    // optional skinfolds for Jackson & Pollock
    private Double chestMm;
    private Double abdomenMm;
    private Double thighMm;
    private Double tricepMm;
    private Double suprailiacMm;

    public enum Gender { MALE, FEMALE }
}
