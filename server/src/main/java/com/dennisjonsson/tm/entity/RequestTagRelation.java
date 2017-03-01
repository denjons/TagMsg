package com.dennisjonsson.tm.entity;

//@Entity
public class RequestTagRelation {
	
	//@NotNull
	private String request;
	
	//@NotNull
	private String tag;
	
	
	public RequestTagRelation() {
		super();
	}
	
	public RequestTagRelation(String request, String tag) {
		super();
		this.request = request;
		this.tag = tag;
	}


	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	

}
