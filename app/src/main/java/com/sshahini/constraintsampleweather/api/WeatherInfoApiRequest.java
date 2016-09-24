package com.sshahini.constraintsampleweather.api;

import android.content.Context;

/**
 * Created by saeed on 7/13/16.
 */
public abstract class WeatherInfoApiRequest extends ApiRequest{



    public WeatherInfoApiRequest(Context context){
        super(context);
    }

    public abstract void sendRequest(double latitude, double longitude);
    public abstract void sendRequest(String cityName);

}

