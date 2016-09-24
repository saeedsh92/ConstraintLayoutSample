package com.sshahini.constraintsampleweather.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

/**
 * Created by saeed on 7/13/16.
 */
public class ApiRequest {
    protected static final int TIME_OUT=15000;
    protected static final String OPEN_WEATHER_MAP_API_KEY="0067ea3ffc9cad0548529afa3639f76f";
    protected Context context;
    public ApiRequest(Context context){
        this.context=context;
    }
    protected void setRetryPolicy(Request request,int timeOut,int maxRetries,int backoffMultiplier){
        request.setRetryPolicy(new DefaultRetryPolicy(timeOut,maxRetries,backoffMultiplier));
    }
    protected void setDefaultRetrtyPolicy(Request request){
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    protected void addToQueue(Request request){
        RequestQueue.getInstance(context).addToRequestQueue(request);
    }
}
