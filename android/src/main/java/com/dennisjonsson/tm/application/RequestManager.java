package com.dennisjonsson.tm.application;

import android.util.Log;

import com.dennisjonsson.tm.model.Request;

import java.util.ArrayList;

/**
 * Created by dennis on 2016-03-15.
 */
public class RequestManager {

    public final String LOG_TAG = "RequestManager";

    public final ArrayList<Request> inbox;
    public final ArrayList<Request> outbox;

    RequestManager() {
        inbox = new ArrayList<>();
        outbox = new ArrayList<>();
    }

    public void setInbox(ArrayList<Request> requests){
        this.inbox.clear();
        this.inbox.addAll(requests);
        Log.d(LOG_TAG, "current size of requests is "+requests.size());
    }

    public void addToInbox(ArrayList<Request> requests){
        this.inbox.addAll(requests);
    }

    public void setOutbox(ArrayList<Request> requests){
        this.outbox.clear();
        this.outbox.addAll(requests);

    }

    public void addToOutbox(ArrayList<Request> requests){
        this.outbox.addAll(requests);
    }


}
