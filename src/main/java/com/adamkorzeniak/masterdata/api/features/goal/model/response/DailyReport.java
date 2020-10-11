package com.adamkorzeniak.masterdata.api.features.goal.model.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyReport {

    private LocalDate date;
    private Double overallDailyRating;
    private List<GoalGroupReport> goalGroupReports;
}
