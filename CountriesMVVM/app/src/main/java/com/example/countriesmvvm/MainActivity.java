package com.example.countriesmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.countriesmvvm.database.Country;
import com.example.countriesmvvm.database.CountryRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    CountryViewModel viewModel;

    RecyclerView recyclerView;
    CountriesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new CountryViewModel(getApplication());

        recyclerView = (RecyclerView) findViewById(R.id.countriesRecyclerView);
        adapter = new CountriesRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        viewModel.getAllCountries().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                if (countries == null || countries.size() == 0) {
                    viewModel.fetchData();
                }
                else {
                    adapter.setAllCountries(countries);
                }
            }
        });

        System.out.println("MAIN ACTIVITY");


    }
}