package com.srm.expensetracker.db;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.srm.expensetracker.models.Expense;


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

        if (resultSet .moveToFirst()) {
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

    public void createExpense(String name, Double amount) {
        openDatabase();
        database.execSQL("CREATE TABLE IF NOT EXISTS Expenses(name VARCHAR, amount double);");
        database.execSQL("INSERT INTO Expenses (name, amount) VALUES('"+name+"','"+amount+"');");
        closeDatabase();
    }

    private void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = context.openOrCreateDatabase(databaseName,Context.MODE_PRIVATE,null);
        }
    }

    private void closeDatabase() {
        database.close();
    }
}