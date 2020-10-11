package com.adamkorzeniak.masterdata.api.features.goal.service;

import java.time.LocalDate;

public class GoalUtils {

    public static boolean isDateValid(LocalDate date, LocalDate validFrom, LocalDate validTo) {
        return !date.isBefore(validFrom) && !date.isAfter(validTo);
    }
}
