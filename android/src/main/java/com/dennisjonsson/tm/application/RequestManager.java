package com.dennisjonsson.tm.application;

import android.util.Log;

import com.dennisjonsson.tm.model.Request;

import java.util.ArrayList;

/**
 * Created by dennis on 2016-03-15.
 */
public class RequestManager {

    public final String LOG_TAG = "RequestManager";

    public final ArrayList<Request> requests;

    RequestManager() {
        requests = new ArrayList<>();
    }

    public void setRequests(ArrayList<Request> requests){
        this.requests.clear();
        this.requests.addAll(requests);
        Log.d(LOG_TAG, "current size of requests is "+requests.size());
    }

    public void addRequests(ArrayList<Request> requests){
        this.requests.addAll(requests);
    }



}
