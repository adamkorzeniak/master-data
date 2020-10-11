package com.adamkorzeniak.masterdata.api.features.goal.model.response;

import lombok.Data;

@Data
public class GoalReport {

    private Long id;
    private String name;
    private String description;
    private Double rating;
    private Double significance;

}
