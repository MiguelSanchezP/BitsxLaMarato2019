package com.miguelsanchezp.bitsxlamarato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;

import static com.miguelsanchezp.bitsxlamarato.FileManipulation.createEmptyConf;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.readFile;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.removeRepetition;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.writeDown;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double Latitude;
    private double Longitude;
//    Button button;
//    Button push;
//    Button buttonExportLocation;
    Menu menu;

    private static final String TAG = "MainActivity";
    static String pathname;
    static boolean primera = true;

    static final int REQUEST_POSITION = 0;
    static final int REQUEST_USERNAMEDATA = 1;
    static final int REQUEST_GENERAL_POSITION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission (this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission (this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String [] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 1);
            Toast.makeText(this, "Please restart the app", Toast.LENGTH_LONG).show();
        }else {
            pathname = this.getFilesDir().getAbsolutePath() + "/";
            checkConf();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            menu = findViewById(R.id.menuPerfil);
//            button = findViewById(R.id.buttonFind);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    establishSSHConnectionRetriever(REQUEST_POSITION);
                }
//            });
//            push = findViewById(R.id.buttonPush);
//            push.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    establishSSHConnectionPusher(REQUEST_POSITION);
//                }
//            });
//            buttonExportLocation = findViewById(R.id.buttonExport);
//            buttonExportLocation.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    exportLastKnownLocation();
//                }
//            });
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuPerfil:
                startNewActivity();
                break;
        }
        return true;
    }

    private void startNewActivity () {
        Intent intent = new Intent (this, User.class);
        startActivity(intent);
    }

    void exportLastKnownLocation(String username) {
        if (username != null) {
            final String USER = username;
//            Log.d(TAG, "exportLastKnownLocation: " + fusedLocationProviderClient.getLastLocation().toString());
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Latitude = location.getLatitude();
                        Longitude = location.getLongitude();
                        String position = USER + "%" + Latitude + "%" + Longitude;
                        writeDown(position, pathname + "positionPersonal.txt");
                        Log.d(TAG, "onSuccess: working");
                        establishSSHConnectionPusher(REQUEST_POSITION);
                        Toast.makeText(getApplicationContext(), "Saved position successfully", Toast.LENGTH_LONG).show();
                    } else {
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
        }else{
            Toast.makeText(getApplicationContext(), "Define a username and try again", Toast.LENGTH_LONG).show();
        }
    }

    private void establishSSHConnectionRetriever (int i) {
         new DownloadFromServer().execute(i);
//         Toast.makeText(this, "begins the ssh retrieval", Toast.LENGTH_LONG).show();
    }

    private void establishSSHConnectionPusher (int i) {
        new UploadToServer().execute(i);
//        Toast.makeText(this, "begins the ssh pushing", Toast.LENGTH_LONG).show();
    }

    private void checkConf () {
        File file = new File (pathname + "conf.txt");
        if (file.exists()) {
            Log.d(TAG, "checkConf: file exists");
            primera = false;
        }else{
            Log.d(TAG, "checkConf: file doesn't exist");
            primera = true;
            createConf();
        }
    }

    private void createConf () {
        Log.d(TAG, "createConf: method called");
        createEmptyConf();
        startNewActivity();
    }

    static void ServerParsing (int i) {
        new MainActivity().establishSSHConnectionPusher(i);
    }

    static void prepareRemoval () {
        new MainActivity().establishSSHConnectionRetriever(REQUEST_POSITION);
        removeRepetition(pathname + "positions.txt");
        new MainActivity().establishSSHConnectionPusher(REQUEST_GENERAL_POSITION);
    }
}
