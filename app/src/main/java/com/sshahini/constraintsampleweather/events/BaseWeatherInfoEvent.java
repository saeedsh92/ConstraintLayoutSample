package com.sshahini.constraintsampleweather.events;

/**
 * Created by saeed on 7/16/16.
 */
public class BaseWeatherInfoEvent {
    private Exception exception;


    public BaseWeatherInfoEvent(Exception exception){
        this.exception=exception;
    }
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
