package com.srm.expensetracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.srm.expensetracker.Models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // todo: fix the datasource
        List<Expense> expenseList = new ArrayList<Expense>();
        expenseList.add(new Expense("name", 2.2));
        expenseList.add(new Expense("name", 4.2));
        expenseList.add(new Expense("name", 5.2));

        RecyclerView.Adapter adapter = new ExpenseListAdapter(expenseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }
}