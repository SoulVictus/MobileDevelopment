package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    protected void onResume() {
        super.onResume();

        crimesRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void onCrimeAdd(View view) {
        Intent newCrimeIntent = new Intent(this, CrimeAddActivity.class);
        startActivity(newCrimeIntent);
    }

}