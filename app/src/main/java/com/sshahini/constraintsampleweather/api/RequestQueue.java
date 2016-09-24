package com.sshahini.constraintsampleweather.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

/**
 * Created by saeed on 7/13/16.
 */

public class RequestQueue {

        private static RequestQueue _inst;
        private Context _context;
        private com.android.volley.RequestQueue _requestQueue;


        public RequestQueue(Context context) {//Instructor
            this._context = context;
            getRequestQueue();
        }

        public static synchronized RequestQueue getInstance(Context context) {// get a new instance of class
            if (_inst == null) {
                _inst = new RequestQueue(context);
            }
            return _inst;
        }

        public <T> void addToRequestQueue(Request<T> request) {// add request to queue
            getRequestQueue().add(request);
        }


        public <T> void addToRequestQueue(Request<T> request,String tag) {// add request to queue
            getRequestQueue().add(request).setTag(tag);
        }

        private com.android.volley.RequestQueue getRequestQueue() {
            if (_requestQueue == null) {
                _requestQueue = Volley.newRequestQueue(_context);
            }
            return _requestQueue;
        }
        public void clearRequestQueue(){
            _requestQueue.cancelAll(new com.android.volley.RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
            Log.i("Request","all http requests was canceled, context: "+_context.toString());
        }
        public void cancelRequest(String tag){
            _requestQueue.cancelAll(tag);
            Log.i("Request", "cancelRequest: all request with tag "+tag+" are canceled");
        }

    }

