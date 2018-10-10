package com.srm.expensetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.srm.expensetracker.Models.Expense;

import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {
    private List<Expense> dataSet;

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView amountTextView;

        ExpenseViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            amountTextView = itemView.findViewById(R.id.amount_text_view);
        }
    }

    ExpenseListAdapter(List<Expense> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ExpenseListAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_expense_entry, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, int position) {
        Expense expense = dataSet.get(position);
        viewHolder.amountTextView.setText(expense.getName());
        viewHolder.nameTextView.setText(expense.getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}