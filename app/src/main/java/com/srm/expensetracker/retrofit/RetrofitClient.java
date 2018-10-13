package com.srm.expensetracker.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit instance() {
        return new Retrofit.Builder()
                .baseUrl("https://example-project-1212.apphost.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
