package com.srm.expensetracker.db;

import com.srm.expensetracker.models.Expense;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Expenses implements Serializable {
    List<Expense> list = new ArrayList<Expense>();
}