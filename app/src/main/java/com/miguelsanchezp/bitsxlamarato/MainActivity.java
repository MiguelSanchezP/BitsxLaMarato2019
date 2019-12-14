package com.miguelsanchezp.bitsxlamarato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import static android.content.ContentValues.TAG;
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//
//import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double Latitude;
    private double Longitude;
    Button button;
    private static final String TAG = "MainActivity";
    static String pathname;

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
//                String [] permissions = {Manifest.permission.INTERNET};
//                ActivityCompat.requestPermissions(this, permissions, 1);
            }else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            button = findViewById(R.id.buttonFind);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayTheLastKnownLocation();
                    establishSSHConnection();
                }
            });
            pathname = this.getFilesDir().getAbsolutePath();
        }
//        establishSSHConnection();
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

    private void establishSSHConnection () {
         new ServerConnection().execute("start");
         Toast.makeText(this, "begins the ssh connection", Toast.LENGTH_LONG).show();

    }
}

class ServerConnection extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            JSch ssh = new JSch();
            Session session = ssh.getSession("miguelsanchezp", "elliot.ddns.net", 22);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setPassword("Hr5zfy23.--.");
            session.setConfig(config);
            session.connect();
            Log.d(TAG, "doInBackground: connection established");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            Log.d(TAG, "doInBackground: channel connected");
            ChannelSftp channelSftp = (ChannelSftp) channel;
//            channelSftp.cd("/home/miguelsanchezp/Documents/folder2/");
//            File f = new File("mydoc.txt");
//            try {
//                channelSftp.put("./mydoc.txt");
//            channelSftp.mkdir("./mydoc.txt");
//                Log.d(TAG, "doInBackground: file created in remote folder");
//            }catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Log.d(TAG, "doInBackground: file not found");
//            }
            channelSftp.cd("/home/miguelsanchezp/Documents/folder/");
            Log.d(TAG, "doInBackground: " + channelSftp.ls(channelSftp.pwd()));
            InputStream is = channelSftp.get("./thisisthesecond");
//            channelSftp.get("miguelsanchezp@elliot.ddns:/home/miguelsanchezp/Documetns/folder/doc.txt", "miguelsanchezp@elliot.ddns.net:/home/miguelsanchezp/Documents/folder2/mydoc.txt");
            try {
                File file  = new File (MainActivity.pathname + "/mydoc.txt");
                Log.d(TAG, "doInBackground: " + MainActivity.pathname);
                FileOutputStream os = new FileOutputStream (file);
                IOUtils.copyStream(is, os);
                os.write(is.toString().getBytes());
                Log.d(TAG, "doInBackground (inputStream): " + is.toString());
                Log.d(TAG, "doInBackground: Transfer made!! :)");
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: There was a filenotfoundexception");
            }catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: There was an IOException");
            }
            channelSftp.exit();
            channel.disconnect();
            session.disconnect();
            Log.d(TAG, "doInBackground: Connections closed");
        }catch (JSchException e) {
            Log.d(TAG, "doInBackground: There was a JSch Exception");
            e.printStackTrace();
        }catch (SftpException e){
            Log.d(TAG, "doInBackground: There was a SftpException");
            e.printStackTrace();
        }
        return null;
    }
}

