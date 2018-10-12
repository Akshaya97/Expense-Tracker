package com.srm.expensetracker.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srm.expensetracker.R;
import com.srm.expensetracker.db.Database;
import com.srm.expensetracker.models.Expense;
import com.srm.expensetracker.models.Income;

import java.text.NumberFormat;
import java.util.List;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Database db = new Database(getActivity());
        Double totalExpense = db.getTotalExpense();
        Double totalIncome = db.getTotalIncome();
        Double netAmount = totalIncome - totalExpense;

        TextView netAmountKeyTextView = view.findViewById(R.id.net_amount_key_text_view);
        TextView netAmountValueTextView = view.findViewById(R.id.net_amount_value_text_view);
        TextView incomeValueTextView = view.findViewById(R.id.income_amount_value_text_view);
        TextView expenseValueTextView = view.findViewById(R.id.expense_amount_value_text_view);
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();

        if (netAmount >= 0) {
            netAmountKeyTextView.setText(R.string.you_have);
            netAmountValueTextView.setText(currencyInstance.format(netAmount));
            netAmountValueTextView.setTextColor(getResources().getColor(R.color.app));
        } else {
            netAmount *= -1;
            netAmountKeyTextView.setText(R.string.you_owe);
            netAmountValueTextView.setText(currencyInstance.format(netAmount));
            netAmountValueTextView.setTextColor(Color.RED);
        }

        incomeValueTextView.setText(currencyInstance.format(totalIncome));
        expenseValueTextView.setText(currencyInstance.format(totalExpense));

        return view;
    }
}