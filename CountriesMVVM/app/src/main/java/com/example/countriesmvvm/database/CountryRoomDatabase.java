package com.example.countriesmvvm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class}, version = 3)
public abstract class CountryRoomDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
    private static CountryRoomDatabase instance;

    public static synchronized CountryRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CountryRoomDatabase.class, "countries_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
