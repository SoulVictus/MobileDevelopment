package com.example.studentcrimeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.studentcrimeapp.database.Crime;

import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private CrimePagerAdapter pagerAdapter;
    private CrimeLab crimeLab;

    private UUID currentCrimeId;
    private Crime currentCrime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Intent intent = getIntent();
        crimeLab = CrimeLab.get(this);
        currentCrimeId = UUID.fromString(intent.getStringExtra("crimeUUID"));
        currentCrime = crimeLab.getCrime(currentCrimeId);

        viewPager = findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter = new CrimePagerAdapter(crimeLab, viewPager, fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(crimeLab.getCrimes().indexOf(currentCrime));
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}


