package com.dennisjonsson.tm.application;

import com.dennisjonsson.tm.model.Request;

import java.util.ArrayList;

/**
 * Created by dennis on 2016-03-15.
 */
public class RequestManager {

    private final ArrayList<Request> requests;

    RequestManager() {
        requests = new ArrayList<>();
    }

    public void setRequests(ArrayList<Request> requests){
        this.requests.clear();
        this.requests.addAll(requests);
    }

    public void addRequests(ArrayList<Request> requests){
        this.requests.addAll(requests);
    }

    public ArrayList<Request> getRequests(){
        return requests;
    }


}
