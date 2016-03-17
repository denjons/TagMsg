package com.dennisjonsson.tm.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by dennis on 2016-03-09.
 */
public class TMApplication extends Application {

    private static TMService service;
    private static RequestManager requestManager;

    @Override
    public void onCreate() {

        service = TMService.getInstance(getApplicationContext());

        super.onCreate();

    }

    public static TMService getTMService(Context context){
        service = TMService.getInstance(context.getApplicationContext());
        return service;
    }

    public static RequestManager getRequestManager(){
        if(requestManager == null)
            requestManager = new RequestManager();
        return requestManager;
    }
}
