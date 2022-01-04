package com.example.countriesmvvm.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "countries")
public class Country {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "country_name")
    private String countryName;

    @ColumnInfo(name = "country_capitol")
    private String countryCapitol;

    Country() {

    }
    Country(String countryName, String countryCapitol) {
        this.countryName = countryName;
        this.countryCapitol = countryCapitol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapitol() {
        return countryCapitol;
    }

    public void setCountryCapitol(String countryCapitol) {
        this.countryCapitol = countryCapitol;
    }
}
