package com.defit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FoodItemDto {
    @NotBlank
    private String name;
    @Positive
    private double grams;
}
