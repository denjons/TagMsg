package com.dennisjonsson.tm.client;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ResponseUpdateDTO {

    @NotNull
    public String request;

    @Min(1)
    @Max(50)
    public int limit;

    @Min(0)
    public int offset;

    public String fromResponse;

    public String beforeResponse;

    public ResponseUpdateDTO(String request, int limit, int offset, String fromResponse, String beforeResponse) {
	super();
	this.request = request;
	this.limit = limit;
	this.offset = offset;
	this.fromResponse = fromResponse;
	this.beforeResponse = beforeResponse;
    }

    public ResponseUpdateDTO() {
	super();
    }

}
