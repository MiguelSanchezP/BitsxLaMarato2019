package com.miguelsanchezp.bitsxlamarato;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.miguelsanchezp.bitsxlamarato.FileManipulation.BIOGRAPHY_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.BIRTHDATE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.CONSENT_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.DIAGNOSED_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.DISEASEDATE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.MAIL_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.MEDICALCONDITION_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.NAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.PHONE_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.SURNAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.USERNAME_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.WEB_FIELD;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.getConfField;
import static com.miguelsanchezp.bitsxlamarato.FileManipulation.modifyConf;

public class User extends AppCompatActivity {
    private String username;
    private String name;
    private String surnames;
    private String illness;
    private boolean diagnosed;
    private String biography;
    private String mail;
    private String phoneNumber;
    private String web;
    private boolean consent;
    private double latitude;
    private double longitude;
    private String beginDate;
    private String birthDate;

    private TextView TVUsername;
    private EditText TEUsername;
    private TextView TVName;
    private EditText TEName;
    private TextView TVSurnames;
    private EditText TESurnames;
    private TextView TVBirthdate;
    private EditText DPBirthdate;
    private TextView TVCondition;
    private EditText TECondition;
    private TextView TVDiagnosed;
    private Switch SDiagnosed;
    private TextView TVBiography;
    private EditText MLTBiography;
    private TextView TVPhone;
    private EditText TEPhone;
    private TextView TVMail;
    private EditText TEMail;
    private TextView TVWeb;
    private EditText TEWeb;
    private TextView TVBeginDate;
    private EditText DPBeginDate;
    private TextView TVConsent;
    private Switch SConsent;
    private Button BOK;
    private Button BLocation;

    public User () {

    }

    public User(String username, double latitude, double longitude) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        TVUsername = findViewById(R.id.TvUsername);
        TEUsername = findViewById(R.id.TeUsername);
        TVName = findViewById(R.id.Tvname);
        TEName = findViewById(R.id.TEname);
        TVSurnames = findViewById(R.id.TvSurnames);
        TESurnames = findViewById(R.id.TeSurnames);
        TVBirthdate = findViewById(R.id.TvBirthdate);
        DPBirthdate = findViewById(R.id.DpBirthdate);
        TVCondition = findViewById(R.id.TvCondition);
        TECondition = findViewById(R.id.TeCondition);
        TVDiagnosed = findViewById(R.id.TvDiagnosed);
        SDiagnosed = findViewById(R.id.SDiagnosed);
        TVBiography = findViewById(R.id.TvBiography);
        MLTBiography = findViewById(R.id.MLTBiography);
        TVPhone = findViewById(R.id.TvPhone);
        TEPhone = findViewById(R.id.TePhone);
        TVMail = findViewById(R.id.TvMail);
        TEMail = findViewById(R.id.TeMail);
        TVWeb = findViewById(R.id.TvWeb);
        TEWeb = findViewById(R.id.TeWeb);
        TVBeginDate = findViewById(R.id.TvBeginDate);
        DPBeginDate = findViewById(R.id.DPBeginDate);
        TVConsent = findViewById(R.id.TvConsent);
        SConsent = findViewById(R.id.SConsent);
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
                new MainActivity().exportLastKnownLocation(getUsername());
            }
        });
        loadPreviousConfig();
    }

    String getUsername () {
        return username;
    }

    String getName() {
        return name;
    }

    String getSurnames() {
        return surnames;
    }

    String getIllness() {
        return illness;
    }

    boolean getDiagnosed() {
        return diagnosed;
    }

    String getBiography() {
        return biography;
    }

    String getMail() {
        return mail;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    String getWeb() {
        return web;
    }

    boolean getConsent() {
        return consent;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    void setUsername (String username) {
        this.username = username;
    }

    void setName(String name) {
        this.name = name;
    }

    void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    void setIllness(String illness) {
        this.illness = illness;
    }

    void setDiagnosed(boolean diagnosed) {
        this.diagnosed = diagnosed;
    }

    void setBiography(String biography) {
        this.biography = biography;
    }

    void setMail(String mail) {
        this.mail = mail;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    void setWeb(String web) {
        this.web = web;
    }

    void setConsentment(boolean consentment) {
        this.consent = consentment;
    }

    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    void setLongitude(double longitude) {
        this.longitude = longitude;
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
}
