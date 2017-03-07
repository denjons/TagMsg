package com.dennisjonsson.tm.client;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestUpdateDTO {

    @Min(1)
    @Max(50)
    public int limit;

    @Min(0)
    public int offset;

    public String fromRequest;

    public String beforeRequest;

    public RequestUpdateDTO() {

    }

    public RequestUpdateDTO(int limit, int offset, String startDate, String endDate) {
	super();
	this.limit = limit;
	this.offset = offset;
	this.fromRequest = startDate;
	this.beforeRequest = endDate;
    }

}
