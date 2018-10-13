package com.srm.expensetracker.retrofit;

import com.srm.expensetracker.models.Expense;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface EntryService {
    @GET("/expenses")
    Call<Expense> getEntries();
}