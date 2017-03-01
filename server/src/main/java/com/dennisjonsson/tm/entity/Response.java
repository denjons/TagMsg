package com.dennisjonsson.tm.entity;

//@Entity
public class Response {
	
	//@NotNull
	private String id;
	
	//@NotNull
	private String tag;
	
	//@NotNull
	private String request;
	
	//@NotNull
	private String text;

	public Response() {
		super();
	}

	public Response(String id, String tag, String request, String text) {
		super();
		this.id = id;
		this.tag = tag;
		this.request = request;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

	
}
