package com.example.countriesmvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM countries")
    LiveData<List<Country>> getAll();

    @Insert
    void insertAll(Country... countries);

    @Insert
    void insert(Country country);

    @Delete
    void delete(Country country);
}
