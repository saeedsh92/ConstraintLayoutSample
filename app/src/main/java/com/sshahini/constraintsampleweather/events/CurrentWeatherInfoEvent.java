package com.sshahini.constraintsampleweather.events;

import com.sshahini.constraintsampleweather.models.WeatherInfo;

/**
 * Created by saeed on 7/16/16.
 */
public class CurrentWeatherInfoEvent extends BaseWeatherInfoEvent {
    private WeatherInfo weatherInfo;

    public CurrentWeatherInfoEvent(WeatherInfo weatherInfo,Exception exception) {
        super(exception);
        this.weatherInfo=weatherInfo;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
