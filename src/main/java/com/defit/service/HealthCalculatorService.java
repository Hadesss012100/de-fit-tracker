package com.defit.service;

import com.defit.dto.FoodItemDto;
import com.defit.dto.MeasurementRequest;
import com.defit.dto.MeasurementRequest.Gender;
import com.defit.dto.MeasurementResponse;
import com.defit.entity.DailyLog;
import com.defit.repository.DailyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HealthCalculatorService {

    private final DailyLogRepository repository;

    private static final Map<String, Integer> FOOD_DB = new HashMap<>();

    static {
        FOOD_DB.put("chicken", 165); // per 100g
        FOOD_DB.put("rice", 130);
        FOOD_DB.put("apple", 52);
        FOOD_DB.put("egg", 155);
    }

    public MeasurementResponse process(MeasurementRequest request) {
        double intake = calculateIntake(request.getFoodIntake());
        double bmr = calculateBmr(request.getGender(), request.getWeightKg(), request.getHeightCm(), request.getAge());
        double tdee = calculateTdee(bmr, request.getSteps(), request.getExperienceYears());
        double tef = intake * 0.1;
        double bmi = request.getWeightKg() / Math.pow(request.getHeightCm() / 100.0, 2);
        double fat = calculateBodyFat(request, bmi);
        double lean = request.getWeightKg() * (1 - fat / 100.0);
        double deficit = intake - tdee;

        DailyLog log = new DailyLog();
        log.setHeightCm(request.getHeightCm());
        log.setWeightKg(request.getWeightKg());
        log.setAge(request.getAge());
        log.setGender(request.getGender());
        log.setExperienceYears(request.getExperienceYears());
        log.setSteps(request.getSteps());
        log.setIntakeCalories(intake);
        log.setBmr(bmr);
        log.setTdee(tdee);
        log.setTef(tef);
        log.setBmi(bmi);
        log.setFatPercent(fat);
        log.setLeanMass(lean);
        log.setDeficit(deficit);
        repository.save(log);

        return MeasurementResponse.builder()
                .bmr(bmr)
                .tdee(tdee)
                .tef(tef)
                .fatPercent(fat)
                .leanMass(lean)
                .bmi(bmi)
                .intakeCalories(intake)
                .deficit(deficit)
                .build();
    }

    private double calculateIntake(List<FoodItemDto> foods) {
        return foods.stream()
                .mapToDouble(f -> FOOD_DB.getOrDefault(f.getName().toLowerCase(), 0) * f.getGrams() / 100.0)
                .sum();
    }

    private double calculateBmr(Gender gender, double weightKg, double heightCm, int age) {
        if (gender == Gender.MALE) {
            return 88.36 + (13.4 * weightKg) + (4.8 * heightCm) - (5.7 * age);
        }
        return 447.6 + (9.2 * weightKg) + (3.1 * heightCm) - (4.3 * age);
    }

    private double calculateTdee(double bmr, long steps, int exp) {
        double activity = 1.2 + Math.min(exp * 0.05, 0.6) + Math.min(steps / 10000.0, 1.0);
        return bmr * activity;
    }

    private double calculateBodyFat(MeasurementRequest req, double bmi) {
        if (req.getChestMm() != null && req.getAbdomenMm() != null && req.getThighMm() != null && req.getGender() == Gender.MALE) {
            double sum = req.getChestMm() + req.getAbdomenMm() + req.getThighMm();
            double density = 1.10938 - 0.0008267 * sum + 0.0000016 * sum * sum - 0.0002574 * req.getAge();
            return 495 / density - 450;
        }
        if (req.getTricepMm() != null && req.getSuprailiacMm() != null && req.getThighMm() != null && req.getGender() == Gender.FEMALE) {
            double sum = req.getTricepMm() + req.getSuprailiacMm() + req.getThighMm();
            double density = 1.0994921 - 0.0009929 * sum + 0.0000023 * sum * sum - 0.0001392 * req.getAge();
            return 495 / density - 450;
        }
        // fallback BMI method
        int sex = req.getGender() == Gender.MALE ? 1 : 0;
        return 1.2 * bmi + 0.23 * req.getAge() - 10.8 * sex - 5.4;
    }
}
