package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView calculatorTextView;
    String textViewText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorTextView = (TextView) findViewById(R.id.textView);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                textViewText += "9";
                break;
            case R.id.button1:
                textViewText += "1";
                break;
            case R.id.button2:
                textViewText += "2";
                break;
            case R.id.button3:
                textViewText += "3";
                break;
            case R.id.button4:
                textViewText += "4";
                break;
            case R.id.button5:
                textViewText += "5";
                break;
            case R.id.button6:
                textViewText += "6";
                break;
            case R.id.button7:
                textViewText += "7";
                break;
            case R.id.button8:
                textViewText += "8";
                break;
            case R.id.button9:
                textViewText += "9";
                break;
        }
        calculatorTextView.setText(textViewText);
    }

    public void clearTextView(View view) {
        calculatorTextView.setText("");
    }
}