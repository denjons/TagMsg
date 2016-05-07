package com.dennisjonsson.tm.client;

/**
 * Created by dennis on 2016-04-02.
 */
public class ResponseUpdateDTO {

    public UserDTO user;

    public String request;

    public int limit;

    public int offset;

    public String fromResponse;

    public String beforeResponse;

    public ResponseUpdateDTO(UserDTO user, String request, int limit, int offset) {
        this.user = user;
        this.request = request;
        this.limit = limit;
        this.offset = offset;
    }
}
