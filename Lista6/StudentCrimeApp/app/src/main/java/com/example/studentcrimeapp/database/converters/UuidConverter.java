package com.example.studentcrimeapp.database.converters;

import androidx.room.TypeConverter;

import java.util.UUID;

public class UuidConverter {

    @TypeConverter
    public static UUID fromString(String value) {
        return value == null ? null : UUID.fromString(value);
    }

    @TypeConverter
    public static String uuidToString(UUID id) {
        return id == null ? null : id.toString();
    }
}
