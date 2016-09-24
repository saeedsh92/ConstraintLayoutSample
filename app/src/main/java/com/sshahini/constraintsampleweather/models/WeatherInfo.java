package com.sshahini.constraintsampleweather.models;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by saeed on 7/12/16.
 */
public class WeatherInfo {
    private static final String TAG = "WeatherInfo";

    //Units of measurement strings for ui
    public static final String TEMPERATURE_CELSIUS = "\u00B0";
    private static final String KM = "KM";

    private int id;
    private String state;
    private String description;
    private String base;
    private float windSpeed;
    private int humidity;
    private int minTemp;
    private int maxTemp;
    private float temperature;
    private String city;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        minTemp-=273.15;
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        maxTemp-=273.15;
        this.maxTemp = maxTemp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMinMaxTempUiText() {
        return minTemp + TEMPERATURE_CELSIUS + " " + maxTemp + TEMPERATURE_CELSIUS;
    }

    public String getWindSpeedUiText() {
        return windSpeed + " " + KM;
    }

    public String getHumidityUiText() {
        return humidity + " %";
    }


    public String getCurrentTempUiText() {
        DecimalFormat decimalFormat=new DecimalFormat("#.##");

        return decimalFormat.format(temperature) + TEMPERATURE_CELSIUS;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        temperature-=273.15;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Nullable
    public static WeatherInfo parseWeatherInfoApiResponse(JSONObject jsonObject) throws JSONException {
        Log.d(TAG, "parseWeatherInfoApiResponse() called with: " + "jsonObject = [" + jsonObject + "]");
        WeatherInfo weatherInfo = new WeatherInfo();
        JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        weatherInfo.setId(weather.getInt("id"));
        weatherInfo.setState(weather.getString("main"));
        weatherInfo.setDescription(weather.getString("description"));

        // TODO: 7/13/16 change this
        weatherInfo.setCity(jsonObject.getString("name"));

        JSONObject main = jsonObject.getJSONObject("main");
        weatherInfo.setHumidity(main.getInt("humidity"));
        weatherInfo.setMinTemp(main.getInt("temp_min"));
        weatherInfo.setMaxTemp(main.getInt("temp_max"));
        weatherInfo.setTemperature((float) main.getDouble("temp"));
        JSONObject wind = jsonObject.getJSONObject("wind");
        weatherInfo.setWindSpeed((float) wind.getDouble("speed"));
        return weatherInfo;
    }

    @Nullable
    public static List<WeatherInfo> parseForecastApiResponse(JSONObject response) throws JSONException {
        Log.d(TAG, "parseWeatherInfoApiResponse() called with: " + "jsonObject = [" + response + "]");
        List<WeatherInfo> weatherInfos = new ArrayList<>();

        String city = response.getJSONObject("city").getString("name");

        JSONArray weathersListJsonArray = response.getJSONArray("list");
        for (int i = 0; i < weathersListJsonArray.length(); i++) {
            WeatherInfo weatherInfo = new WeatherInfo();
            JSONObject weatherInfoJsonObject = weathersListJsonArray.getJSONObject(i);

            Log.i(TAG, "parseForecastApiResponse: date:" + weatherInfoJsonObject.getLong("dt"));
            weatherInfo.setDate(new SimpleDateFormat("EEEE").format(new Date(weatherInfoJsonObject.getLong("dt") * 1000)));
            weatherInfo.setMinTemp(weatherInfoJsonObject.getJSONObject("temp").getInt("min"));
            weatherInfo.setMaxTemp(weatherInfoJsonObject.getJSONObject("temp").getInt("max"));
            weatherInfo.setState(weatherInfoJsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
            weatherInfo.setId(weatherInfoJsonObject.getJSONArray("weather").getJSONObject(0).getInt("id"));
            weatherInfo.setCity(city);
            weatherInfos.add(weatherInfo);
        }

        return weatherInfos;
    }


}
