package com.defit.controller;

import com.defit.dto.MeasurementRequest;
import com.defit.dto.MeasurementResponse;
import com.defit.entity.DailyLog;
import com.defit.repository.DailyLogRepository;
import com.defit.service.HealthCalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HealthController {

    private final HealthCalculatorService service;
    private final DailyLogRepository repository;

    @PostMapping("/calculate")
    public MeasurementResponse calculate(@Valid @RequestBody MeasurementRequest request) {
        return service.process(request);
    }

    @GetMapping("/logs")
    public List<DailyLog> logs() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingDouble(DailyLog::getDeficit))
                .toList();
    }
}
