package com.miguelsanchezp.bitsxlamarato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double Latitude;
    private double Longitude;
    Button button;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String [] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 1);
            Toast.makeText(this, "Please restart the app", Toast.LENGTH_LONG).show();
        }else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            button = findViewById(R.id.buttonFind);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayTheLastKnownLocation();
                }
            });
        }
    }

    private void displayTheLastKnownLocation () {
        Log.d(TAG, "displayTheLastKnownLocation: " + fusedLocationProviderClient.getLastLocation().toString());
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Latitude = location.getLatitude();
                    Longitude = location.getLongitude();
                    String position = Latitude + ", " + Longitude;
                    Log.d(TAG, "onSuccess: working");
                    Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "The location was null", Toast.LENGTH_LONG).show();
                }
            }
        });
        fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "There was an error with the location", Toast.LENGTH_LONG).show();
            }
        });
    }
}
