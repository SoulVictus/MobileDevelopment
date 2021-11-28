package com.example.studentcrimeapp;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(String title, boolean isSolved, Date date) {
        mId = UUID.randomUUID();
        mTitle = title;
        mSolved = isSolved;
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() { return mDate.toString(); }

    public boolean getIsSolved() { return mSolved; }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSolved(boolean isSolved) {
        mSolved = isSolved;
    }

    public void setDate(Date date) { mDate = date; }
}
