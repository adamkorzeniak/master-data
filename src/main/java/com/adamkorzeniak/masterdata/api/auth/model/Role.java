package com.adamkorzeniak.masterdata.api.auth.model;

public enum Role {

    USER, ADMIN;

    public boolean isUser() {
        return this.equals(USER);
    }

    public boolean isAdmin() {
        return this.equals(ADMIN);
    }
}