package com.srm.expensetracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.srm.expensetracker.R;
import com.srm.expensetracker.db.Database;

public class EntryInputActivity extends Activity {
    private TextView nameInputView;
    private TextView amountInputView;
    private Boolean isExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_input);
        nameInputView = findViewById(R.id.name_input_view);
        amountInputView = findViewById(R.id.amount_input_view);
        isExpense = getIntent().getBooleanExtra("isExpense", false);

        if (isExpense) {
            setTitle(R.string.add_expense_entry);
            nameInputView.setHint(R.string.expense_name);
        } else {
            setTitle(R.string.add_income_entry);
            nameInputView.setHint(R.string.income_name);
        }
        amountInputView.setHint(R.string.amount);
    }

    public void cancelPressed(View view) {
        finish();
    }

    public void confirmPressed(View view) {
        String name = nameInputView.getText().toString();
        String amountString = amountInputView.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Integer message = isExpense ? R.string.expense_name_empty : R.string.income_name_empty;
            Toast.makeText(getApplicationContext(), getString(message), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(amountString)) {
            Toast.makeText(getApplicationContext(), getString(R.string.amount_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        Double amount = Double.parseDouble(amountString);
        if (amount <= 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.amount_is_zero), Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        if (isExpense) {
            db.createExpense(name, amount);
        } else {
            // todo: Change to income
        }

        Integer message = isExpense ? R.string.expense_entry_added : R.string.income_entry_added;
        Toast.makeText(getApplicationContext(), getString(message), Toast.LENGTH_SHORT).show();
        finish();
    }
}