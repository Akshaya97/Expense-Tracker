package com.srm.expensetracker.Models;

public class Expense {
    private String name;
    private Double amount;

    public Expense(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}