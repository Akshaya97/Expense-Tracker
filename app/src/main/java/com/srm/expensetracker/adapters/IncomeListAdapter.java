package com.srm.expensetracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.srm.expensetracker.R;
import com.srm.expensetracker.models.Income;

import java.util.List;

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.IncomeViewHolder> {
    private List<Income> dataSet;

    class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView amountTextView;

        IncomeViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            amountTextView = itemView.findViewById(R.id.amount_text_view);
        }
    }

    public IncomeListAdapter(List<Income> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public IncomeListAdapter.IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_income_entry, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IncomeViewHolder viewHolder, int position) {
        Income income = dataSet.get(position);
        viewHolder.nameTextView.setText(income.getName());
        viewHolder.amountTextView.setText(income.getAmountInCurrency());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}