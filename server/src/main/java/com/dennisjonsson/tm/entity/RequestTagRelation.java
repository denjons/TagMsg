package com.dennisjonsson.tm.entity;

import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@IdClass(RequestTagRelation.class)
@Table(name = "REQUEST_TAG_RELATION")
public class RequestTagRelation {

    @NotNull
    private int request;

    @NotNull
    private String tag;

    public RequestTagRelation(int request, String tag) {
	super();
	this.request = request;
	this.tag = tag;
    }

    public RequestTagRelation() {
	super();
    }

    public int getRequest() {
	return request;
    }

    public void setRequest(int request) {
	this.request = request;
    }

    public String getTag() {
	return tag;
    }

    public void setTag(String tag) {
	this.tag = tag;
    }

}
