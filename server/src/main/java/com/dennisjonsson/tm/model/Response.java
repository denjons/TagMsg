package com.dennisjonsson.tm.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
	
	private String id, user, request;
	public String content;
	public Date date;
	
	public Response(String id, String user, String request, Date date){
		this.id = id;
		this.user = user;
		this.request = request;
		this.date = date;
	}
	
	public String getId(){
		return id;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getRequest(){
		return request;
	}
	
	public Date getDate(){
		return date;
	}
}
