package com.sshahini.constraintsampleweather.events;

/**
 * Created by saeed on 7/16/16.
 */
public class LocationChangedEvent {
    private double latitude;
    private double longitude;

    public LocationChangedEvent(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
