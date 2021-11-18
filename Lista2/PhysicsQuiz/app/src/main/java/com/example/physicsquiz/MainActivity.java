package com.example.physicsquiz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView questionView;
    private TextView questionsAmountView;
    private TextView scoreView;

    private Button trueButton;
    private Button falseButton;

    private int currentQuestionId = 0;
    private int questionsAmount;
    private int penaltyValue = 0;
    private String[] questionArray;
    private String[] answersArray;
    private HashMap<Integer, Boolean> userAnswers = new HashMap<Integer, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionView = findViewById(R.id.questionView);
        questionsAmountView = findViewById(R.id.questionAmountView);
        scoreView = findViewById(R.id.scoreView);

        trueButton = findViewById(R.id.buttonTrue);
        trueButton.setBackgroundColor(Color.GREEN);

        falseButton = findViewById(R.id.buttonFalse);
        falseButton.setBackgroundColor(Color.RED);

        questionArray = getResources().getStringArray(R.array.questions_array);
        answersArray = getResources().getStringArray(R.array.answers_array);
        questionsAmount = questionArray.length;

        if (savedInstanceState != null) {
            userAnswers = (HashMap<Integer, Boolean>) savedInstanceState.getSerializable("userAnswers");
            currentQuestionId = savedInstanceState.getInt("currentQuestionId");
            penaltyValue = savedInstanceState.getInt("penaltyValue");
            setButtonsState();
            checkAnswers();
        }

        questionView.setText(questionArray[currentQuestionId]);

        questionsAmountView.setText(getResources().getString(R.string.questions_amount, currentQuestionId+1, questionsAmount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("userAnswers", userAnswers);
        outState.putInt("currentQuestionId", currentQuestionId);
        outState.putInt("penaltyValue", penaltyValue);
    }

    ActivityResultLauncher<Intent> cheatActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        penaltyValue += data.getIntExtra("penaltyValue", 0);
                        if (penaltyValue > 100)
                                penaltyValue = 100;
                    }
                }
            }
    );

    public void onChangeQuestion(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                currentQuestionId += 1;
                break;
            case R.id.buttonBack:
                currentQuestionId -= 1;
                break;
        }

        if (currentQuestionId < 0)
            currentQuestionId = questionsAmount - 1;
        if (currentQuestionId >= questionsAmount)
            currentQuestionId = 0;

        questionView.setText(questionArray[currentQuestionId]);
        // set questions amount view
        questionsAmountView.setText(getResources().getString(R.string.questions_amount, currentQuestionId+1, questionsAmount));

        setButtonsState();
    }

    public void onCheat(View view) {
        Intent intent = new Intent(this, cheatActivity.class);
        intent.putExtra("currentQuestionId", currentQuestionId);
        intent.putExtra("hasAnswered", userAnswers.get(currentQuestionId) != null);

        cheatActivityResultLauncher.launch(intent);
    }

    public void onChooseAnswer(View view) {
        boolean answer = false;

        switch (view.getId()) {
            case R.id.buttonTrue:
                answer = true;
                break;
            case R.id.buttonFalse:
                answer = false;
                break;
        }

        userAnswers.put(currentQuestionId, answer);

        setButtonsState();
        checkAnswers();
    }

    public void onSearch(View view) {
        Intent browser = new Intent(Intent.ACTION_WEB_SEARCH);
        browser.putExtra(SearchManager.QUERY, questionArray[currentQuestionId]);
        startActivity(browser);
    }

    public void onRestart(View view) {
        currentQuestionId = 0;
        penaltyValue = 0;
        userAnswers.clear();

        questionView.setText(questionArray[currentQuestionId]);
        questionsAmountView.setText(getResources().getString(R.string.questions_amount, currentQuestionId+1, questionsAmount));

        findViewById(R.id.scoreLayout).setVisibility(View.INVISIBLE);
        setButtonsState();
    }

    public void setButtonsState() {
        if (userAnswers.get(currentQuestionId) != null) {
            trueButton.setEnabled(false);
            falseButton.setEnabled(false);

            if (userAnswers.get(currentQuestionId) == true) {
                trueButton.setBackgroundColor(Color.GRAY);
                trueButton.setTextColor(Color.GREEN);

                falseButton.setBackgroundColor(Color.GRAY);
                falseButton.setTextColor(Color.WHITE);
            }
            else {
                falseButton.setBackgroundColor(Color.GRAY);
                falseButton.setTextColor(Color.RED);

                trueButton.setBackgroundColor(Color.GRAY);
                trueButton.setTextColor(Color.WHITE);
            }
        }
        else {
            trueButton.setEnabled(true);
            trueButton.setBackgroundColor(Color.GREEN);
            trueButton.setTextColor(Color.WHITE);

            falseButton.setEnabled(true);
            falseButton.setBackgroundColor(Color.RED);
            falseButton.setTextColor(Color.WHITE);
        }
    }

    public void checkAnswers() {
        if (userAnswers.values().size() == questionsAmount) {
            int points = 0;
            for (int i = 0; i < questionsAmount; i++) {
                if (userAnswers.get(i) == Boolean.parseBoolean(answersArray[i])) {
                    points += 1;
                }
            }

            int percents = Math.round(((float)points / questionsAmount) * 100);
            scoreView.setText(getResources().getString(R.string.score_string, points, questionsAmount, percents, penaltyValue, percents - penaltyValue));
            findViewById(R.id.scoreLayout).setVisibility(View.VISIBLE);
        }
    }
}