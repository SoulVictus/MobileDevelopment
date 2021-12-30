package com.example.studentcrimeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.studentcrimeapp.database.Crime;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private CrimePagerAdapter pagerAdapter;
    private CrimeLab crimeLab;

    ActivityResultLauncher<Intent> cameraIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        ImageView  currentImageView = (ImageView) viewPager.getChildAt(0).findViewById(R.id.crimeImageView);
                        currentImageView.setImageBitmap(imageBitmap);

                        // create byte[] to save image to database
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        crimeLab.getCrimes().get(viewPager.getCurrentItem()).setCrimeImage(bos.toByteArray());
                        crimeLab.updateCrime(crimeLab.getCrimes().get(viewPager.getCurrentItem()).getId());

                        Toast.makeText(CrimePagerActivity.this, "Photo added!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Intent intent = getIntent();
        crimeLab = CrimeLab.get(this);
        UUID currentCrimeId = UUID.fromString(intent.getStringExtra("crimeUUID"));
        Crime currentCrime = crimeLab.getCrime(currentCrimeId);

        viewPager = findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter = new CrimePagerAdapter(crimeLab, viewPager, cameraIntent, this, fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(crimeLab.getCrimes().indexOf(currentCrime));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.launch(takePhotoIntent);
                } else {
                    // action when permission denied
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}


