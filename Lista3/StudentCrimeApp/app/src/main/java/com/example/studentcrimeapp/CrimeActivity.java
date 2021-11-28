package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {
    private UUID currentCrimeId;
    private Crime currentCrime;
    private CrimeLab crimeLab;


    private CheckBox solvedCheckBox;
    private EditText titleEditText;
    private Button dateEditButton;

    private SwitchDateTimeDialogFragment dateTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        solvedCheckBox = (CheckBox) findViewById(R.id.isSolvedCheckBox);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        titleEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                currentCrime.setTitle(v.getText().toString());
                return false;
            }
        });
        dateEditButton = (Button) findViewById(R.id.dateEditButton);

        crimeLab = CrimeLab.get(this);
        Intent intent = getIntent();

        currentCrimeId = UUID.fromString(intent.getStringExtra("crimeUUID"));
        currentCrime = crimeLab.getCrime(currentCrimeId);

        setDatePicker();

        solvedCheckBox.setChecked(currentCrime.getIsSolved());
        dateEditButton.setText(currentCrime.getDate().toString());
    }

    public void onCheckBoxClick(View view) {
        if (view.getId() == R.id.isSolvedCheckBox) {
            solvedCheckBox.setChecked(!currentCrime.getIsSolved());
            currentCrime.setSolved(!currentCrime.getIsSolved());
        }
    }

    public void onDeleteButtonClick(View view) {
        if (view.getId() == R.id.addButton) {
            crimeLab.deleteCrime(currentCrimeId);
            finish();
        }
    }

    private void setDatePicker() {
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag("TAG_DATETIME_FRAGMENT");
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {

        }
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                currentCrime.setDate(date);
                dateEditButton.setText(date.toString());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists

            }
        });

        dateEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.setDefaultDateTime(new GregorianCalendar().getTime());
                dateTimeFragment.show(getSupportFragmentManager(), "TAG_DATETIME_FRAGMENT");
            }
        });
    }
}