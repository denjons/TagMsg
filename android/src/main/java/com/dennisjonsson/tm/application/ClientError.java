package com.dennisjonsson.tm.application;

/**
 * Created by dennis on 2016-03-09.
 */
public class ClientError {
    int responseCode;
    String body;
    Exception e;

    public ClientError(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;

    }
}
