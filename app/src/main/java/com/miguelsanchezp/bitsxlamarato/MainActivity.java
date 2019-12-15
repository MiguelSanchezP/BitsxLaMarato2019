package com.miguelsanchezp.bitsxlamarato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.miguelsanchezp.bitsxlamarato.FileManipulation.GenerateNullProfile;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.USERNAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.createEmptyConf;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.getProfileField;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.isARealPoint;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.removeRepetition;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private static final String TAG = "MainActivity";
    static String pathname;
    static boolean primera = true;
    static Context context;
    static final int REQUEST_POSITION = 0;
    static final int REQUEST_USERNAMEDATA = 1;
    static final int REQUEST_GENERAL_POSITION = 2;
    static final int REQUEST_RANDOM_GENERATED = 3;

    static String profile;
    static boolean editable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 1);
            Toast.makeText(this, "Please restart the app", Toast.LENGTH_LONG).show();
        } else {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
            pathname = this.getFilesDir().getAbsolutePath() + "/";
            context = getApplicationContext();
            checkConf();
        }
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
                editable = true;
                startNewActivity();
                break;
        }
        return true;
    }

    private void startNewActivity () {
        Intent intent = new Intent (this, User.class);
        startActivity(intent);
    }

    private void establishSSHConnectionRetriever (int i) {
        try {
            new DownloadFromServer().execute(i).get();
        }catch(ExecutionException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void establishSSHConnectionPusher (int i) {
        new UploadToServer().execute(i);
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
        Log.d(TAG, "ServerParsing: action completed");
    }

    static void ServerRetrieving (int i) {
        new MainActivity().establishSSHConnectionRetriever(i);
        Log.d(TAG, "ServerRetrieving: action completed");
    }

    static void prepareRemoval () {
        new MainActivity().establishSSHConnectionRetriever(REQUEST_POSITION);
        removeRepetition(pathname + "positions.txt");
        new MainActivity().establishSSHConnectionPusher(REQUEST_GENERAL_POSITION);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<MarkerOptions> markers = FileManipulation.getMarkers();
        if (markers != null) {
            for (MarkerOptions m : markers) {
                Marker marker = googleMap.addMarker(m);
                marker.setTag(m.getTitle());
            }
        }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag() != null) {
                    if (isARealPoint(marker.getPosition())) {
                        Log.d(TAG, "onMarkerClick: is real");
                        profile = marker.getTag().toString();
                        ServerRetrieving(REQUEST_USERNAMEDATA);
                        editable = false;
                    }else{
                        Log.d(TAG, "onMarkerClick: is irreal");
                        GenerateNullProfile();
                        editable = false;
                    }
                        Log.d(TAG, "onMarkerClick: " + getProfileField(USERNAME_FIELD));
                        startNewActivity();
                    }
                return true;
            }
        });
    }
}
