package com.example.studentcrimeapp.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.studentcrimeapp.database.converters.BitmapConverter;
import com.example.studentcrimeapp.database.converters.DateConverter;
import com.example.studentcrimeapp.database.converters.UuidConverter;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "crime")
public class Crime {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @TypeConverters(UuidConverter.class)
    private UUID mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "solved")
    private boolean mSolved;

    @ColumnInfo(name = "image")
    private byte[] mCrimeImage;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(String title, boolean isSolved, Date date, byte[] image) {
        mId = UUID.randomUUID();
        mTitle = title;
        mSolved = isSolved;
        mDate = date;
        mCrimeImage = image;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() { return mDate; }

    public byte[] getCrimeImage() { return mCrimeImage; }

    public boolean getSolved() { return mSolved; }

    public void setId(UUID id) { mId = id; }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSolved(boolean isSolved) {
        mSolved = isSolved;
    }

    public void setDate(Date date) { mDate = date; }

    public void setCrimeImage(byte[] image) { mCrimeImage = image; }
}
