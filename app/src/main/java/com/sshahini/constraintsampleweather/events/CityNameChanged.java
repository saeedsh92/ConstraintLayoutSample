package com.sshahini.constraintsampleweather.events;

/**
 * Created by saeed on 7/16/16.
 */
public class CityNameChanged {
    private String newCityName;
    public CityNameChanged(String newcityName){
        this.newCityName=newcityName;
    }

    public String getNewCityName() {
        return newCityName;
    }

    public void setNewCityName(String newCityName) {
        this.newCityName = newCityName;
    }
}
