package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int count = 0;
    private TextView counterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterView = findViewById(R.id.textView);
    }

    public void countUp(View view) {
        count += 1;
        counterView.setText(String.valueOf(count));
    }

    public void countDown(View view) {
        count -= 1;
        counterView.setText(String.valueOf(count));
    }

    public void resetCounter(View view) {
        count = 0;
        counterView.setText(String.valueOf(count));
    }
}