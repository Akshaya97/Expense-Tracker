package com.srm.expensetracker.db;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.srm.expensetracker.models.Expense;
import com.srm.expensetracker.models.Income;


public class Database {
    private SQLiteDatabase database;
    private String databaseName = "expense_tracker_db";
    private Context context;

    public Database(Context context) {
        this.context = context;
    }

    public List<Expense> getAllExpenses() {
        openDatabase();
        String query = "Select name, amount from Expenses";
        Cursor resultSet = database.rawQuery(query,null);
        List<Expense> list = new ArrayList<Expense>();

        if (resultSet.moveToFirst()) {
            while (!resultSet.isAfterLast()) {
                Expense expense = new Expense();
                expense.setName(resultSet.getString(resultSet.getColumnIndex("name")));
                expense.setAmount(resultSet.getDouble(resultSet.getColumnIndex("amount")));
                list.add(expense);
                resultSet.moveToNext();
            }
        }

        resultSet.close();
        closeDatabase();

        return list;
    }

    public Double getTotalIncome() {
        openDatabase();
        String query = "Select SUM(amount) as Total from Incomes";
        Cursor resultSet = database.rawQuery(query, null);

        if (resultSet.moveToFirst()) {
            return resultSet.getDouble(resultSet.getColumnIndex("Total"));
        }
        return 0.0;
    }

    public Double getTotalExpense() {
        openDatabase();
        String query = "Select SUM(amount) as Total from Expenses";
        Cursor resultSet = database.rawQuery(query, null);

        if (resultSet.moveToFirst()) {
            return resultSet.getDouble(resultSet.getColumnIndex("Total"));
        }
        return 0.0;
    }

    public List<Income> getAllIncomes() {
        openDatabase();
        String query = "Select name, amount from Incomes";
        Cursor resultSet = database.rawQuery(query,null);
        List<Income> list = new ArrayList<Income>();

        if (resultSet .moveToFirst()) {
            while (!resultSet.isAfterLast()) {
                Income income= new Income();
                income.setName(resultSet.getString(resultSet.getColumnIndex("name")));
                income.setAmount(resultSet.getDouble(resultSet.getColumnIndex("amount")));
                list.add(income);
                resultSet.moveToNext();
            }
        }

        resultSet.close();
        closeDatabase();

        return list;
    }

    public void createExpense(String name, Double amount) {
        openDatabase();
        database.execSQL("INSERT INTO Expenses (name, amount) VALUES('"+name+"','"+amount+"');");
        closeDatabase();
    }

    public void createIncome(String name, Double amount) {
        openDatabase();
        database.execSQL("INSERT INTO Incomes (name, amount) VALUES('"+name+"','"+amount+"');");
        closeDatabase();
    }

    public void clearData() {
        openDatabase();
        database.execSQL("Drop Table if exists Expenses");
        database.execSQL("Drop Table if exists Incomes");
        closeDatabase();
    }

    private void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = context.openOrCreateDatabase(databaseName,Context.MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Expenses(name VARCHAR, amount double);");
            database.execSQL("CREATE TABLE IF NOT EXISTS Incomes(name VARCHAR, amount double);");
        }
    }

    private void closeDatabase() {
        database.close();
    }
}