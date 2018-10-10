package com.srm.expensetracker.models;

import java.text.NumberFormat;

public class Expense {
    private String name;
    private Double amount;

    public Expense() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Expense(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAmountInCurrency() {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        return currencyInstance.format(amount);
    }

    public String getName() {
        return name;
    }
}