package com.sshahini.constraintsampleweather.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sshahini.constraintsampleweather.events.CurrentWeatherInfoEvent;
import com.sshahini.constraintsampleweather.models.WeatherInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saeed on 7/13/16.
 */
public class CurrentWeatherInfoRequest extends WeatherInfoApiRequest {
    private static final String TAG = "CurrentWeather";

    public CurrentWeatherInfoRequest(Context context) {
        super(context);
    }


    @Override
    public void sendRequest(final double latitude, final double longitude) {
        Log.d(TAG, "getWeather() called with: " + "latitude = [" + latitude + "], longitude = [" + longitude + "]");
        send(getApiUrl(latitude, longitude));
    }

    @Override
    public void sendRequest(String cityName) {
        Log.d(TAG, "sendRequest() called with: " + "cityName = [" + cityName + "]");
        send(getApiUrl(cityName));
    }

    private void send(String apiUrl) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    WeatherInfo weatherInfo = WeatherInfo.parseWeatherInfoApiResponse(response);
                    EventBus.getDefault().post(new CurrentWeatherInfoEvent(weatherInfo, null));
                } catch (JSONException e) {
                    EventBus.getDefault().post(new CurrentWeatherInfoEvent(null, e));
                    Log.e(TAG, "onResponse: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new CurrentWeatherInfoEvent(null, error));
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        });
        setDefaultRetrtyPolicy(request);
        addToQueue(request);
    }

    private String getApiUrl(double latitude, double longitude) {
        return "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&mode=json&cnt=10" + "&APPID=" + ApiRequest.OPEN_WEATHER_MAP_API_KEY;
    }

    private String getApiUrl(String cityName) {
        return "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&mode=json&cnt=10" + "&APPID=" + ApiRequest.OPEN_WEATHER_MAP_API_KEY;
    }

}
