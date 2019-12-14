package com.miguelsanchezp.bitsxlamarato;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class User extends AppCompatActivity {
    private String id;
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

    public User (String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
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










    }

    String getId () {
        return id;
    }
    String getName () {
        return name;
    }
    String getSurnames () {
        return surnames;
    }
    String getIllness () {
        return illness;
    }
    boolean getDiagnosed () {
        return diagnosed;
    }
    String getBiography () {
        return biography;
    }
    String getMail () {
        return mail;
    }
    String getPhoneNumber () {
        return phoneNumber;
    }
    String getWeb () {
        return web;
    }
    boolean getConsent () {
        return consent;
    }
    double getLatitude () {
        return latitude;
    }
    double getLongitude() {
        return longitude;
    }
    Date getBeginDate () {
        return beginDate;
    }
    Date getBirthDate () {
        return birthDate;
    }

    void setId (String id) {
        this.id = id;
    }
    void setName (String name) {
        this.name = name;
    }
    void setSurnames (String surnames) {
        this.surnames = surnames;
    }
    void setIllness (String illness) {
        this.illness = illness;
    }
    void setDiagnosed (boolean diagnosed) {
        this.diagnosed = diagnosed;
    }
    void setBiography (String biography) {
        this.biography = biography;
    }
    void setMail (String mail) {
        this.mail = mail;
    }
    void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    void setWeb (String web) {
        this.web = web;
    }
    void setConsentment (boolean consentment) {
        this.consent = consentment;
    }
    void setLatitude (double latitude) {
        this.latitude = latitude;
    }
    void setLongitude (double longitude) {
        this.longitude = longitude;
    }
    void setBeginDate (Date beginDate) {
        this.beginDate = beginDate;
    }
    void setBirthDate (Date birthDate) {
        this.birthDate = birthDate;
    }
}
