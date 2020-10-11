package com.adamkorzeniak.masterdata.api.features.goal.controller;

import com.adamkorzeniak.masterdata.api.features.goal.model.response.DailyReport;
import com.adamkorzeniak.masterdata.api.features.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/feature/Goal")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/report")
    public ResponseEntity<List<DailyReport>> calculateReports(
            @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
            ) {
        List<DailyReport> response = goalService.calculateReport(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
