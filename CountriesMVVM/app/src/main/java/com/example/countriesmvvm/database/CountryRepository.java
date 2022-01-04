package com.example.countriesmvvm.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class CountryRepository {
    private CountryDao countryDao;
    private LiveData<List<Country>> allCountries;

    public CountryRepository(Application application) {
        CountryRoomDatabase db = CountryRoomDatabase.getInstance(application);
        countryDao = db.countryDao();

        System.out.println("REPOSITORY");
        allCountries = countryDao.getAll();
    }

    public void fetchData() {
        ApiController controller = new ApiController();

        CountryAPI countryAPI = controller.getClient().create(CountryAPI.class);
        Call<List<CountryApiModel>> call = countryAPI.loadCountries();

        call.enqueue(new Callback<List<CountryApiModel>>() {
            @Override
            public void onResponse(Call<List<CountryApiModel>> call, Response<List<CountryApiModel>> response) {
                System.out.println("RESPONSE");
                if (response.isSuccessful()) {
                    System.out.println("GET DATA FROM RESPONSE");
                    List<CountryApiModel> listFromAPI = response.body();
                    for (int i = 0; i < listFromAPI.size(); i++) {
                        String name = listFromAPI.get(i).getName().getCommon();
                        String capital;
                        if (listFromAPI.get(i).getCapital() != null)
                            capital = listFromAPI.get(i).getCapital().get(0);
                        else
                            capital = "None";

                        Country newCountry = new Country(name, capital);

                        countryDao.insert(newCountry);
                    }
                    allCountries = countryDao.getAll();
                }
                else {
                    System.out.println("RESPONSE FAILED");
                }
            }

            @Override
            public void onFailure(Call<List<CountryApiModel>> call, Throwable t) {
                System.out.println("RESPONSE FAILED");
            }
        });
    }

    public LiveData<List<Country>> getAllCountries() {
        return allCountries;
    }
}

