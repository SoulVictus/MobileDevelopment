package com.example.factorialmultithreadapp;

import java.math.BigInteger;

public class FactorialThread extends Thread {
    int startIndex;
    int endIndex;
    BigInteger result;

    FactorialThread(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void run() {
        result = new BigInteger(Integer.toString(startIndex));
        for (int i = startIndex + 1; i < endIndex; i++)
        {
            result = result.multiply(BigInteger.valueOf(i));
        }
    }

    public BigInteger getResult() {
        return result;
    }
}
