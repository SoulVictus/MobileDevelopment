package com.example.studentcrimeapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Crime.class, exportSchema = false, version = 1)
public abstract class CrimeDatabase extends RoomDatabase {
    private static final String DB_NAME = "crime_db";
    private static CrimeDatabase instance;

    public static synchronized CrimeDatabase getInstance(Context context) {
        if (instance == null) {
           instance = Room.databaseBuilder(context.getApplicationContext(), CrimeDatabase.class, DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build();
        }
        return instance;
    }

    public abstract CrimeDao crimeDao();
}
