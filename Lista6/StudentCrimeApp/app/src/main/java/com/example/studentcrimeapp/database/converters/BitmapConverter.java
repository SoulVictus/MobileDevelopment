package com.example.studentcrimeapp.database.converters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class BitmapConverter {
    @TypeConverter
    public static Bitmap fromBlob(byte[] value) {
        if (value != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(value, 0, value.length);
            return bitmap;
        }
        else
            return null;
    }

    @TypeConverter
    public static byte[] bitmapToBlob(Bitmap value) {

        if (value != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            value.compress(Bitmap.CompressFormat.PNG, 100, bos);

            return bos.toByteArray();
        }
        else
            return null;
    }
}
