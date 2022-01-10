package com.example.factorialmultithreadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BigInteger factorialResult;
    Button calculateButton;
    TextView factorialResultTextView;
    EditText numberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateButton = (Button) findViewById(R.id.calculateButton);
        factorialResultTextView = (TextView) findViewById(R.id.factorialResultView);
        numberInput = (EditText) findViewById(R.id.numberInput);
    }

    public void onButtonClick(View view) {
        if (numberInput.getText() == null)
            return;

        factorialResult = new BigInteger("1");
        int typedNumber = Integer.parseInt(numberInput.getText().toString());

        int numberOfThreads = (typedNumber > 20) ? Runtime.getRuntime().availableProcessors() : 1;

        ArrayList<FactorialThread> threadList = new ArrayList<FactorialThread>();

        int step = typedNumber / numberOfThreads;
        int offValue = typedNumber - (step*numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            if (i != numberOfThreads - 1)
                threadList.add(new FactorialThread((i * step)+1, (i * step) + step + 1));
            else
                threadList.add(new FactorialThread((i * step)+1, (i * step) + step + 1 + offValue));
            threadList.get(i).start();
        }

        for(int i = 0; i < numberOfThreads; i++) {
            try {
                threadList.get(i).join();
            }
            catch (InterruptedException e) {
                threadList.get(i).interrupt();
            }
        }

        for (int i = 0 ; i < numberOfThreads; i++) {
            factorialResult = factorialResult.multiply(threadList.get(i).getResult());
        }

        factorialResultTextView.setText(factorialResult.toString());

    }
}