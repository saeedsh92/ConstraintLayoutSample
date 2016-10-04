package com.sshahini.constraintsampleweather.controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sshahini.constraintsampleweather.events.LocationChangedEvent;
import com.sshahini.constraintsampleweather.events.OnLocationPermissionDeniedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by saeed on 7/12/16.
 */
public class LocationController implements LocationListener {

    private static final String TAG = "LocationController";

    private Context context;
    private LocationManager locationManager;
    private static final long MIN_TIME_FOR_UPDATE = 10000;
    private static final long MIN_DISTANCE_UPDATE = 1000;
    public static final int LOCATION_PERMISSION_REQUEST_CODE=999;

    public LocationController(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "LocationController: location permission denied!!!");
                ((AppCompatActivity) context).requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }
        }
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_UPDATE, this);

    }

    public Location getCurrentLocation() {
        Location gpsLocation = getLocationProvider(LocationManager.GPS_PROVIDER);
        if (gpsLocation == null) {
            return getLocationProvider(LocationManager.NETWORK_PROVIDER);//return network location
        } else {
            return gpsLocation;//return gps location
        }
    }

    @Nullable
    public Location getLocationProvider(String provider) {
        Location location = null;
        if (locationManager != null) {
            if (locationManager.isProviderEnabled(provider)) {
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "You are not granted location service permission", Toast.LENGTH_LONG).show();
                    return null;
                }
                location = locationManager.getLastKnownLocation(provider);
            }
        } else {
            EventBus.getDefault().post(new OnLocationPermissionDeniedEvent());
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChangedEvent() called with: " + "location = [" + location + "]");
        EventBus.getDefault().post(new LocationChangedEvent(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
