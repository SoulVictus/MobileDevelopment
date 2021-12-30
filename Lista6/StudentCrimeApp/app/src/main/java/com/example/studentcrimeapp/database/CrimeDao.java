package com.example.studentcrimeapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CrimeDao {
    @Query("SELECT * FROM crime")
    List<Crime> getCrimeList();

    @Insert
    void insertCrime(Crime crime);

    @Update
    void updateCrime(Crime crime);

    @Delete
    void deleteCrime(Crime crime);

}
