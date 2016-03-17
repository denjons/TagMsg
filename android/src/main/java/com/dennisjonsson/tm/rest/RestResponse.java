package com.dennisjonsson.tm.rest;

/**
 * Created by dennis on 2016-03-10.
 */
public class RestResponse {

    enum Error{
        ERROR_CONNECTION_TIMEOUT,
        ERROR_GET_INPUT_STREAM,
        ERROR_MALFORMED_REQUEST,
        ERROR_SERVER_INTERNAL_ERROR
    }

    private final int responseCode;
    private final String body;
    public final Error error;
    public Object result;

    public RestResponse(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
        this.error = null;
    }

    public RestResponse(int responseCode, String body, Error error) {
        this.responseCode = responseCode;
        this.body = body;
        this.error = error;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getBody() {
        return body;
    }
}
