package com.defit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementResponse {
    private double bmr;
    private double tdee;
    private double tef;
    private double fatPercent;
    private double leanMass;
    private double bmi;
    private double intakeCalories;
    private double deficit;
}
