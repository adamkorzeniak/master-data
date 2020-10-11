package com.adamkorzeniak.masterdata.api.features.goal.model.response;

import lombok.Data;

import java.util.List;

@Data
public class GoalGroupReport {

    private Long id;
    private String name;
    private String description;
    private Double rating;
    private Double significance;
    private List<GoalReport> goalReports;
}
