package com.sshahini.constraintsampleweather.utils;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;

import com.sshahini.constraintsampleweather.R;

/**
 * Created by saeed on 7/16/16.
 * this class get weather id and return appropriate drawable
 */
public class WeatherIconParser {
    private Context context;
    public WeatherIconParser(Context context){
        this.context=context;
    }
    public VectorDrawableCompat getIcon(int weatherId){

        String firstNumber=String.valueOf(weatherId).substring(0,1);
        switch (firstNumber){
            case "2":
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_thunderstorm,null);
            case "3":
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_rain,null);
            case "5":
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_shower_rain,null);
            case "6":
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_snow,null);
            case "7":
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_mist,null);
            case "8":
                if (weatherId==800)
                    return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_clearsky,null);
                else
                if (weatherId==801)
                    return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_fewclouds,null);
                else
                    return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_cloudy,null);
            case "9":
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_wind,null);
            default:
                return VectorDrawableCompat.create(context.getResources(), R.drawable.ic_clearsky,null);
        }
    }
}
