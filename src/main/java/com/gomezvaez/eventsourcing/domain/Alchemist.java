package com.gomezvaez.eventsourcing.domain;

import java.util.ArrayList;
import java.util.List;

public class Alchemist {
    private String id;
    private String name;
    private String email;
    private int pearls;
    private final List<Activity> activities;
    private final List<PearlExpense> pearlExpenses;

    // Getters and Setters

    public List<Activity> getActivities() {
        return activities;
    }

    public List<PearlExpense> getPearlsSpendings() {
        return pearlExpenses;
    }

    public Alchemist() {
        activities = new ArrayList<>();
        pearlExpenses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPearls() {
        return pearls;
    }

    public void setPearls(int pearls) {
        this.pearls = pearls;
    }
}