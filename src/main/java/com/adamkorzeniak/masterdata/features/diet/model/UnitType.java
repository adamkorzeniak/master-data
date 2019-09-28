package com.adamkorzeniak.masterdata.features.diet.model;

public enum UnitType {
    GRAM, MILILITR;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
