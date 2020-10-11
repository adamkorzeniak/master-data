package com.adamkorzeniak.masterdata.api.features.goal.model.response;

import lombok.Data;

import java.util.List;

@Data
public class GoalReportResponse {

    private Double overallRating;
    private List<GoalGroupReport> goalGroupReports;
}
