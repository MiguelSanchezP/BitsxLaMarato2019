package com.miguelsanchezp.bitsxlamarato;

import java.util.Date;

public class User {
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
    private Date beginDate;
    private Date birthDate;

    public User (String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
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
