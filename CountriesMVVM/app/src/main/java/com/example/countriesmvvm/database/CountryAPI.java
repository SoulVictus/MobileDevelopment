package com.example.countriesmvvm.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface CountryAPI {
    @GET("all")
    Call<List<CountryApiModel>> loadCountries();
}
