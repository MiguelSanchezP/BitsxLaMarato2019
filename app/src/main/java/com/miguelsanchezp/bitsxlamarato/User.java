package com.miguelsanchezp.bitsxlamarato;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.miguelsanchezp.bitsxlamarato.FileManipulation.BIOGRAPHY_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.BIRTHDATE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.CONSENT_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.DIAGNOSED_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.DISEASEDATE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.LATITUDE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.LONGITUDE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.MAIL_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.MEDICALCONDITION_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.NAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.PHONE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.SURNAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.USERNAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.WEB_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.getConfField;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.getProfileField;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.modifyConf;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.removeRepetition;
//import static com.miguelsanchezp.bitsxlamarato.FileManipulation.renameEntryOf;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.writeDown;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.REQUEST_POSITION;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.REQUEST_RANDOM_GENERATED;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.REQUEST_USERNAMEDATA;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.ServerParsing;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.editable;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.pathname;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.prepareRemoval;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.primera;

public class User extends AppCompatActivity {

    private EditText TEUsername;
    private EditText TEName;
    private EditText TESurnames;
    private EditText DPBirthdate;
    private TextView TVCondition;
    private EditText TECondition;
    private Switch SDiagnosed;
    private EditText MLTBiography;
    private EditText TEPhone;
    private EditText TEMail;
    private EditText TEWeb;
    private EditText DPBeginDate;
    private Switch SConsent;
    private Button BLocation;
    private Button BOK;

    private double Longitude;
    private double Latitude;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String TAG = "User";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        TEUsername = findViewById(R.id.TeUsername);
        TEName = findViewById(R.id.TEname);
        TESurnames = findViewById(R.id.TeSurnames);
        DPBirthdate = findViewById(R.id.DpBirthdate);
        TVCondition = findViewById(R.id.TvCondition);
        TECondition = findViewById(R.id.TeCondition);
        SDiagnosed = findViewById(R.id.SDiagnosed);
        MLTBiography = findViewById(R.id.MLTBiography);
        TEPhone = findViewById(R.id.TePhone);
        TEMail = findViewById(R.id.TeMail);
        TEWeb = findViewById(R.id.TeWeb);
        DPBeginDate = findViewById(R.id.DPBeginDate);
        SConsent = findViewById(R.id.SConsent);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        BOK = findViewById(R.id.buttonok);
        BOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseStuff();
                Toast.makeText(getApplicationContext(), "All files saved successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        BLocation = findViewById(R.id.buttonLocation);
        BLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                            modifyConf(LATITUDE_FIELD, String.valueOf(Latitude));
                            modifyConf(LONGITUDE_FIELD, String.valueOf(Longitude));
                            String position = getConfField(USERNAME_FIELD) + "%" + Latitude + "%" + Longitude;
                            writeDown(position, pathname + "positionPersonal.txt");
                            Log.d(TAG, "onSuccess: working");
                            ServerParsing(REQUEST_POSITION);
                            prepareRemoval();
                            ServerParsing(REQUEST_RANDOM_GENERATED);
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
                Toast.makeText(getApplicationContext(), "Define a username and try again", Toast.LENGTH_LONG).show();
            }
        });
        TVCondition.setEnabled(false);
        TECondition.setEnabled(false);
        SDiagnosed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    TVCondition.setEnabled(false);
                    TECondition.setEnabled(false);
                }else{
                    TVCondition.setEnabled(true);
                    TECondition.setEnabled(true);
                }
            }
        });
        if (!primera) {
            loadPreviousConfig();
        }
        if (!editable) {
            loadProfile();
        }
    }

    private AppCompatActivity getActivity () {
        return this;
    }

    void parseStuff () {
        if (TEUsername.getText() != null) {
            modifyConf (USERNAME_FIELD, TEUsername.getText().toString());
        }
        if (TEName.getText() != null) {
            modifyConf (NAME_FIELD, TEName.getText().toString());
        }
        if (TESurnames.getText() != null) {
            modifyConf (SURNAME_FIELD, TESurnames.getText().toString());
        }
        if (SDiagnosed.isChecked()) {
            modifyConf(DIAGNOSED_FIELD, "true");
        }else{
            modifyConf(DIAGNOSED_FIELD, "false");
        }
        if (TECondition.getText() != null) {
            modifyConf(MEDICALCONDITION_FIELD, TECondition.getText().toString());
        }
        if (MLTBiography.getText() != null) {
            modifyConf(BIOGRAPHY_FIELD, MLTBiography.getText().toString());
        }
        if (TEMail.getText() != null) {
            modifyConf(MAIL_FIELD, TEMail.getText().toString());
        }
        if (TEPhone.getText() != null) {
            modifyConf(PHONE_FIELD, TEPhone.getText().toString());
        }
        if (TEWeb.getText() != null) {
            modifyConf(WEB_FIELD, TEWeb.getText().toString());
        }
        if (SConsent.isChecked()) {
            modifyConf(CONSENT_FIELD, "true");
        }else{
            modifyConf(CONSENT_FIELD, "false");
        }
        if (DPBirthdate.getText() != null) {
            modifyConf(BIRTHDATE_FIELD, DPBirthdate.getText().toString());
        }
        if (DPBeginDate.getText() != null) {
            modifyConf(DISEASEDATE_FIELD, DPBeginDate.getText().toString());
        }
        ServerParsing(REQUEST_USERNAMEDATA);
    }

    private void loadPreviousConfig () {
        if (getConfField(USERNAME_FIELD) != null) {
            TEUsername.setText(getConfField(USERNAME_FIELD));
        }
        if (getConfField(NAME_FIELD) != null) {
            TEName.setText(getConfField(NAME_FIELD));
        }
        if (getConfField(SURNAME_FIELD) != null) {
            TESurnames.setText(getConfField(SURNAME_FIELD));
        }
        if (getConfField(DIAGNOSED_FIELD) != null) {
            if (getConfField(DIAGNOSED_FIELD).equals("true")) {
                SDiagnosed.setChecked(true);
            }else{
                SDiagnosed.setChecked(false);
            }
        }
        if (getConfField(MEDICALCONDITION_FIELD) != null) {
            TECondition.setText(getConfField(MEDICALCONDITION_FIELD));
        }
        if (getConfField(BIOGRAPHY_FIELD) != null) {
            MLTBiography.setText(getConfField(BIOGRAPHY_FIELD));
        }
        if (getConfField(MAIL_FIELD) != null) {
            TEMail.setText(getConfField(MAIL_FIELD));
        }
        if (getConfField(PHONE_FIELD) != null) {
            TEPhone.setText(getConfField(PHONE_FIELD));
        }
        if (getConfField(WEB_FIELD) != null) {
            TEWeb.setText(getConfField(WEB_FIELD));
        }
        if (getConfField(CONSENT_FIELD) != null) {
            if (getConfField(CONSENT_FIELD).equals("true")) {
                SConsent.setChecked(true);
            }else{
                SConsent.setChecked(false);
            }
        }
        if (getConfField(BIRTHDATE_FIELD) != null) {
            DPBirthdate.setText(getConfField(BIRTHDATE_FIELD));
        }
        if (getConfField(DISEASEDATE_FIELD) != null) {
            DPBeginDate.setText(getConfField(DISEASEDATE_FIELD));
        }
    }
 //used does not exist or doesn't want to share
    private void loadProfile () {
        if (getProfileField(USERNAME_FIELD) != null) {
            TEUsername.setText(getProfileField(USERNAME_FIELD));
        }
        if (SConsent.isChecked()) {
            if (getProfileField(NAME_FIELD) != null) {
                TEName.setText(getProfileField(NAME_FIELD));
            }
            if (getProfileField(SURNAME_FIELD) != null) {
                TESurnames.setText(getProfileField(SURNAME_FIELD));
            }
            if (getProfileField(DIAGNOSED_FIELD) != null) {
                if (getProfileField(DIAGNOSED_FIELD).equals("true")) {
                    SDiagnosed.setChecked(true);
                } else {
                    SDiagnosed.setChecked(false);
                }
            }
            if (getProfileField(MEDICALCONDITION_FIELD) != null) {
                TECondition.setText(getProfileField(MEDICALCONDITION_FIELD));
            }
            if (getProfileField(BIOGRAPHY_FIELD) != null) {
                MLTBiography.setText(getProfileField(BIOGRAPHY_FIELD));
            }
            if (getProfileField(MAIL_FIELD) != null) {
                TEMail.setText(getProfileField(MAIL_FIELD));
            }
            if (getProfileField(PHONE_FIELD) != null) {
                TEPhone.setText(getProfileField(PHONE_FIELD));
            }
            if (getProfileField(WEB_FIELD) != null) {
                TEWeb.setText(getProfileField(WEB_FIELD));
            }
            if (getProfileField(CONSENT_FIELD) != null) {
                if (getProfileField(CONSENT_FIELD).equals("true")) {
                    SConsent.setChecked(true);
                } else {
                    SConsent.setChecked(false);
                }
            }
            if (getProfileField(BIRTHDATE_FIELD) != null) {
                DPBirthdate.setText(getProfileField(BIRTHDATE_FIELD));
            }
            if (getProfileField(DISEASEDATE_FIELD) != null) {
                DPBeginDate.setText(getProfileField(DISEASEDATE_FIELD));
            }
            TEUsername.setEnabled(false);
            TEName.setEnabled(false);
            TESurnames.setEnabled(false);
            SDiagnosed.setEnabled(false);
            TECondition.setEnabled(false);
            MLTBiography.setEnabled(false);
            TEMail.setEnabled(false);
            TEWeb.setEnabled(false);
            SConsent.setEnabled(false);
            DPBirthdate.setEnabled(false);
            DPBeginDate.setEnabled(false);
            TEPhone.setEnabled(false);
            BOK.setVisibility(View.GONE);
            BLocation.setVisibility(View.GONE);
        }else{
            TEName.setVisibility(View.GONE);
            TESurnames.setVisibility(View.GONE);
            SDiagnosed.setVisibility(View.GONE);
            TECondition.setVisibility(View.GONE);
            MLTBiography.setVisibility(View.GONE);
            TEMail.setVisibility(View.GONE);
            TEWeb.setVisibility(View.GONE);
            SConsent.setVisibility(View.GONE);
            DPBirthdate.setVisibility(View.GONE);
            DPBeginDate.setVisibility(View.GONE);
            TEPhone.setVisibility(View.GONE);
            BOK.setVisibility(View.GONE);
            BLocation.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), TEUsername.getText().toString() + " hasn't allowed others to see the information ", Toast.LENGTH_LONG).show();
        }
    }
}

