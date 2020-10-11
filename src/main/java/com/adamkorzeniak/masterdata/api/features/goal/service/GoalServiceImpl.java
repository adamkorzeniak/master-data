package com.adamkorzeniak.masterdata.api.features.goal.service;

import com.adamkorzeniak.masterdata.api.features.goal.model.entity.*;
import com.adamkorzeniak.masterdata.api.features.goal.model.response.DailyReport;
import com.adamkorzeniak.masterdata.api.features.goal.model.response.GoalGroupReport;
import com.adamkorzeniak.masterdata.api.features.goal.model.response.GoalReport;
import com.adamkorzeniak.masterdata.api.features.goal.repository.GoalGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class GoalServiceImpl implements GoalService {

    private static final double BEST_RATING = 10;
    private static final double GOOD_RATING = 7;
    private static final double BAD_RATING = 3;
    private static final double MIN_RATING = 0;

    private final GoalGroupRepository goalGroupRepository;

    @Autowired
    public GoalServiceImpl(GoalGroupRepository goalGroupRepository) {
        this.goalGroupRepository = goalGroupRepository;
    }

    @Override
    public List<DailyReport> calculateReport(LocalDate startDate, LocalDate endDate) {
        List<GoalGroup> goalGroups = goalGroupRepository.findAll();
        List<DailyReport> result = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DailyReport dailyReport = new DailyReport();
            dailyReport.setDate(date);
            List<GoalGroupReport> goalGroupReports = new ArrayList<>();
            for (GoalGroup goalGroup : goalGroups) {
                if (!isValidConfiguration(goalGroup.getGoalGroupConfigurations(), date)) {
                    continue;
                }
                GoalGroupReport goalGroupReport = new GoalGroupReport();
                goalGroupReport.setId(goalGroup.getId());
                goalGroupReport.setName(goalGroup.getName());
                goalGroupReport.setDescription(goalGroup.getDescription());
                goalGroupReport.setSignificance(goalGroup.getSignificance());
                List<GoalReport> goalReports = new ArrayList<>();

                for (Goal goal : goalGroup.getGoals()) {
                    if (!isValidConfiguration(goal.getGoalConfigurations(), date)) {
                        continue;
                    }
                    Optional<GoalRatingConfiguration> goalRatingConfiguration = goal.getRatingConfiguration(date);
                    if (goalRatingConfiguration.isEmpty()) {
                        throw new RuntimeException();
                    }
                    GoalReport goalReport = new GoalReport();
                    goalReport.setId(goal.getId());
                    goalReport.setName(goal.getName());
                    goalReport.setDescription(goal.getDescription());
                    goalReport.setSignificance(goal.getSignificance());
                    OptionalDouble goalValue = calculateGoalValue(goal, date);
                    goalReport.setRating(0.0);
                    if (goalValue.isPresent()) {
                        double rating = calculateRating(goalValue.getAsDouble(), goalRatingConfiguration.get());
                        goalReport.setRating(rating);
                    }
                    goalReports.add(goalReport);
                }
                goalGroupReport.setGoalReports(goalReports);
                if (!goalReports.isEmpty()) {
                    double goalGroupRatingWages = goalReports.stream()
                            .mapToDouble(GoalReport::getSignificance)
                            .sum();
                    double goalGroupRatingSum = goalReports.stream()
                            .mapToDouble(GoalReport::getRating)
                            .sum();
                    goalGroupReport.setRating(goalGroupRatingSum / goalGroupRatingWages);
                }
                goalGroupReports.add(goalGroupReport);
            }
            if (!goalGroupReports.isEmpty()) {
                double goalGroupRatingWages = goalGroupReports.stream()
                        .mapToDouble(GoalGroupReport::getSignificance)
                        .sum();
                double goalGroupRatingSum = goalGroupReports.stream()
                        .mapToDouble(GoalGroupReport::getRating)
                        .sum();
//                goalGroupReport.setRating(goalGroupRatingSum / goalGroupRatingWages);
            }
//            result.setGoalGroupReports(goalGroupReports);
            result.add(dailyReport);
        }

        return result;
    }

    private OptionalDouble calculateGoalValue(Goal goal, LocalDate date) {
        if (goal.getGoalElements().isEmpty()) {
            return OptionalDouble.empty();
        }
        double goalValue = 0;
        for (GoalElement element : goal.getGoalElements()) {
            double entriesSum = element.getGoalElementEntries().stream()
                    .filter(e -> date.isEqual(e.getEntryDate()))
                    .mapToDouble(GoalElementEntry::getValue).sum();
            goalValue += element.getMultiplier() * entriesSum;
        }
        return OptionalDouble.of(goalValue);
    }

    private boolean isValidConfiguration(Set<? extends ValidDayPeriod> goalGroupConfigurations, LocalDate date) {
        return goalGroupConfigurations.stream()
                .anyMatch(conf -> GoalUtils.isDateValid(date,  conf.getValidFrom(), conf.getValidTo()));
    }

    private double calculateRating(double goalValue, GoalRatingConfiguration goalRatingConfiguration) {
        double worstValue = goalRatingConfiguration.getWorstValue();
        double badValue = goalRatingConfiguration.getBadValue();
        double goodValue = goalRatingConfiguration.getGoodValue();
        double bestValue = goalRatingConfiguration.getBestValue();

        boolean isAscending = goalRatingConfiguration.getWorstValue() < goalRatingConfiguration.getBadValue();
        if (isAscending && goalValue < worstValue || !isAscending && goalValue > worstValue) {
            return MIN_RATING;
        }
        if (isAscending && goalValue < badValue || !isAscending && goalValue > badValue) {
            return (goalValue - worstValue) / (badValue - worstValue) * (BAD_RATING - MIN_RATING) + MIN_RATING;
        }
        if (isAscending && goalValue < goodValue || !isAscending && goalValue > goodValue) {
            return (goalValue - badValue) / (goodValue - badValue) * (GOOD_RATING - BAD_RATING) + BAD_RATING;
        }
        if (isAscending && goalValue < bestValue || !isAscending && goalValue > bestValue) {
            return (goalValue - goodValue) / (bestValue - goodValue) * (BEST_RATING - GOOD_RATING) + GOOD_RATING;
        }
        return BEST_RATING;
    }
}
