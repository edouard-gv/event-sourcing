package com.gomezvaez.eventsourcing.domain;

import java.util.ArrayList;
import java.util.List;

public class Alchemist {
    private AlchemistId id;
    private String name;
    private String email;
    private int balance;
    private final List<Activity> activities;
    private final List<Expense> expenses;

    // Getters and Setters

    public List<Activity> activities() {
        return activities;
    }

    public List<Expense> expenses() {
        return expenses;
    }

    public Alchemist() {
        activities = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    public AlchemistId getId() {
        return id;
    }

    public void setId(AlchemistId id) {
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }
}