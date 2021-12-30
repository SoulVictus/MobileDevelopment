package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.studentcrimeapp.database.Crime;

public class CrimeListActivity extends AppCompatActivity {

    RecyclerView crimesRecyclerView;
    CrimesRecyclerViewAdapter crimesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crimesRecyclerView = (RecyclerView) findViewById(R.id.crimesRecyclerView);
        crimesRecyclerViewAdapter = new CrimesRecyclerViewAdapter(CrimeLab.get(this));
        crimesRecyclerView.setAdapter(crimesRecyclerViewAdapter);
        crimesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        crimesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                crimesRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        crimesRecyclerViewAdapter.refreshListToView();
        crimesRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void onCrimeAdd(View view) {
        Intent newCrimeIntent = new Intent(this, CrimeAddActivity.class);
        startActivity(newCrimeIntent);
    }

}