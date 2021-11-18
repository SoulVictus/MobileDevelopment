package com.example.physicsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class cheatActivity extends AppCompatActivity {

    TextView answerTextView;
    Intent parentIntent;
    String answerValue = "";

    int currentQuestionId;
    boolean hasAnswered;
    int penaltyValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        parentIntent = getIntent();

        answerTextView = findViewById(R.id.answerTextView);
        currentQuestionId = parentIntent.getIntExtra("currentQuestionId", 0);
        hasAnswered = parentIntent.getBooleanExtra("hasAnswered", false);

        if (savedInstanceState != null) {
            penaltyValue = savedInstanceState.getInt("penaltyValue");
            answerValue = savedInstanceState.getString("answerValue");
            answerTextView.setText(answerValue);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("penaltyValue", penaltyValue);
        outState.putString("answerValue", answerValue);
    }

    public void onShow(View view) {
        answerValue = getResources().getStringArray(R.array.answers_array)[currentQuestionId];
        answerTextView.setText(answerValue);

        if (hasAnswered)
            penaltyValue = 0;
        else
            penaltyValue = 15;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle pressing back button on topbar
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("penaltyValue", penaltyValue);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}