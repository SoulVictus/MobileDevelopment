package com.example.studentcrimeapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class CrimeAddActivity extends AppCompatActivity {
    private CrimeLab crimeLab;

    private String title = "";
    private boolean isSolved = false;
    private Date crimeDate = new GregorianCalendar().getTime();
    private byte[] image;

    private CheckBox solvedCheckBox;
    private EditText titleEditText;
    private ImageView crimeImageView;
    private Button addCrimeImageButton;
    private Button dateEditButton;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    ActivityResultLauncher<Intent> cameraIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        crimeImageView.setImageBitmap(imageBitmap);

                        // create byte[] to save image to database
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        image = bos.toByteArray();

                        Toast.makeText(CrimeAddActivity.this, "Photo added!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_add);

        solvedCheckBox = (CheckBox) findViewById(R.id.isSolvedCheckBox);
        crimeImageView = (ImageView) findViewById(R.id.crimeImageView);
        addCrimeImageButton = (Button) findViewById(R.id.addImageButton);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        titleEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                title = titleEditText.getText().toString();
                return false;
            }
        });
        dateEditButton = (Button) findViewById(R.id.dateEditButton);
        setDatePicker();

        crimeLab = CrimeLab.get(this);
    }

    public void onCheckBoxClick(View view) {
        if (view.getId() == R.id.isSolvedCheckBox) {
            isSolved = !isSolved;
            solvedCheckBox.setChecked(isSolved);
        }
    }

    public void onAddImageButtonClick(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissionCheck == -1)
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.CAMERA}, 200);
        else {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.launch(takePhotoIntent);
        }
    }

    public void onAddButtonClick(View view) {
        if (view.getId() == R.id.deleteButton) {
            if (title.equals("")) {
                titleEditText.setError("Title is required");
            }
            else {
                crimeLab.addCrime(title, isSolved, crimeDate, image);
                finish();
            }
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
                crimeDate = date;
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