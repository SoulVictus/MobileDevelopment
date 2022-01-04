package com.example.countriesmvvm.database;

import java.util.List;

public class CountryApiModel {
    private int id;

    private Name name;
    private List<String> capital = null;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }
}

class Name {
    private String common;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }
}
