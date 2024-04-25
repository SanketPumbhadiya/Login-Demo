package com.example.demo_login.model;


import java.util.Date;

public class ModelLatLongTime {
    double latitude, longitude;
    String time;

    public ModelLatLongTime(double latitude, double longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }
}