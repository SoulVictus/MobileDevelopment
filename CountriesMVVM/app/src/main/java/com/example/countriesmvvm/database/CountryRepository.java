package com.example.countriesmvvm.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository {
    private CountryDao countryDao;
    private LiveData<List<Country>> allCountries;

    public CountryRepository(Application application) {
        CountryRoomDatabase db = CountryRoomDatabase.getInstance(application);
        countryDao = db.countryDao();
        allCountries = countryDao.getAll();
    }

    public void fetchData() {
        ApiController controller = new ApiController();

        CountryService countryService = controller.getClient().create(CountryService.class);
        Call<List<CountryApiModel>> call = countryService.loadCountries();

        call.enqueue(new Callback<List<CountryApiModel>>() {
            @Override
            public void onResponse(Call<List<CountryApiModel>> call, Response<List<CountryApiModel>> response) {
                if (response.isSuccessful()) {
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

