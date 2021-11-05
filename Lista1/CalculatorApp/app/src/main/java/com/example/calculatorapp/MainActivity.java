package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView calculatorTextView;
    TextView resultTextView;
    String textViewText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorTextView = (TextView) findViewById(R.id.textView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        if (savedInstanceState != null) {
            textViewText = savedInstanceState.getCharSequence("textView").toString();
            calculatorTextView.setText(textViewText);
            resultTextView.setText(savedInstanceState.getCharSequence("resultView"));
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("textView", textViewText);
        outState.putCharSequence("resultView", resultTextView.getText());
    }

    public void addNumber(View view) {
        switch (view.getId()) {
            case R.id.button0:
                if (textViewText.endsWith("/"))
                    break;
                else
                    textViewText += "0";
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

    public void addOperator(View view) {
        int id = view.getId();
        String operator = "";

        switch (id) {
            case R.id.buttonAdd:
                operator = "+";
                break;
            case R.id.buttonSub:
                operator = "-";
                break;
            case R.id.buttonMulti:
                operator = "*";
                break;
            case R.id.buttonDiv:
                operator = "/";
                break;
        }

        if (textViewText == "") {
            if (operator == "-") {
                textViewText += "-";
            }
            else
                return;
        }
        else if (
                textViewText.endsWith("+") ||
                textViewText.endsWith("-") ||
                textViewText.endsWith("*") ||
                textViewText.endsWith("/")
        ) {
            textViewText = textViewText.substring(0, textViewText.length()-1) + operator;
        }
        else {
            textViewText += operator;
        }

        calculatorTextView.setText(textViewText);
    }


    public void clearTextView(View view) {
        textViewText = "";
        calculatorTextView.setText("");
    }

    public void calculateResult(View view) {
        if (textViewText == "" || textViewText.endsWith("+") || textViewText.endsWith("-") || textViewText.endsWith("*") || textViewText.endsWith("/"))
            return;

        double result = evalString(textViewText);
        resultTextView.setText(Double.toString(result));
        textViewText = "";
        calculatorTextView.setText("");
    }

    public static double evalString(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}