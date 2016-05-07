package com.dennisjonsson.tm.client;

/**
 * Created by dennis on 2016-03-09.
 */
public class RequestUpdateDTO {



    public UserDTO user;
    public int limit;
    public int offset;
    public String fromRequest;
    public String beforeRequest;

    public RequestUpdateDTO(UserDTO user, int limit, int offset) {
        this.user = user;
        this.limit = limit;
        this.offset = offset;
    }
}