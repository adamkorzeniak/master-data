package com.adamkorzeniak.masterdata.api.features.goal.service;

import com.adamkorzeniak.masterdata.api.features.goal.model.response.DailyReport;

import java.time.LocalDate;
import java.util.List;

public interface GoalService {

    List<DailyReport> calculateReport(LocalDate startDate, LocalDate endDate);
}
