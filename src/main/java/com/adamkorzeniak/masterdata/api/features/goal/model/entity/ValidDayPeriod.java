package com.adamkorzeniak.masterdata.api.features.goal.model.entity;

import java.time.LocalDate;

public interface ValidDayPeriod {

    public LocalDate getValidFrom();

    public LocalDate getValidTo();
}
