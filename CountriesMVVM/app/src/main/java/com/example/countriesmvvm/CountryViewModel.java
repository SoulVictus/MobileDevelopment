package com.example.countriesmvvm;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesmvvm.database.Country;
import com.example.countriesmvvm.database.CountryRepository;

import java.util.List;

public class CountryViewModel extends ViewModel {
    LiveData<List<Country>> allCountries;

    private CountryRepository repository;

    public CountryViewModel(Application application) {
        repository = new CountryRepository(application);
        allCountries = repository.getAllCountries();
    }

    public void fetchData() {
        repository.fetchData();
    }

    LiveData<List<Country>> getAllCountries() {
        return allCountries;
    }
}
