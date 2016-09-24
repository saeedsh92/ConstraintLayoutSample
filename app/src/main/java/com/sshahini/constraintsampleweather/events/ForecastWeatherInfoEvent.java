package com.sshahini.constraintsampleweather.events;

import com.sshahini.constraintsampleweather.models.WeatherInfo;

import java.util.List;

/**
 * Created by saeed on 7/16/16.
 */
public class ForecastWeatherInfoEvent extends BaseWeatherInfoEvent {
    private int id=0;
    private List<WeatherInfo> weatherInfo;

    public ForecastWeatherInfoEvent(List<WeatherInfo> weatherInfo, Exception exception) {
        super(exception);
        this.weatherInfo=weatherInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WeatherInfo> getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(List<WeatherInfo> weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
