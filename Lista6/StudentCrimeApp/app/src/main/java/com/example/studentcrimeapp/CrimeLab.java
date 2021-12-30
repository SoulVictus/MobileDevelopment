package com.example.studentcrimeapp;

import android.content.Context;
import android.util.Log;

import com.example.studentcrimeapp.database.Crime;
import com.example.studentcrimeapp.database.CrimeDao;
import com.example.studentcrimeapp.database.CrimeDatabase;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private CrimeDatabase db;
    private CrimeDao crimeDao;
    private List<Crime> mCrimes;

    private CrimeLab(Context context) {
        db = CrimeDatabase.getInstance(context);
        crimeDao = db.crimeDao();

        if (crimeDao.getCrimeList().size() != 0)
            // get data from database
            mCrimes = crimeDao.getCrimeList();
        else {
            // create dummy database*/
            for (int i = 0; i < 10; i++) {
                Crime crime = new Crime();
                crime.setTitle("Crime #" + i);
                crime.setSolved(i % 2 == 0);
                crimeDao.insertCrime(crime);
            }
            mCrimes = crimeDao.getCrimeList();
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void reloadCrimes() {
        mCrimes = crimeDao.getCrimeList();
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }

        return null;
    }

    public void addCrime(String title, boolean isSolved, Date date, byte[] image) {
        Crime crime = new Crime(title, isSolved, date, image);
        mCrimes.add(crime);
        crimeDao.insertCrime(crime);
    }

    public void updateCrime(UUID id) {
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(id)) {
                crimeDao.updateCrime(mCrimes.get(i));
                break;
            }
        }
    }

    public void deleteCrime(UUID id) {
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(id)) {
                crimeDao.deleteCrime(mCrimes.get(i));
                mCrimes.remove(i);
                break;
            }
        }
    }


}
