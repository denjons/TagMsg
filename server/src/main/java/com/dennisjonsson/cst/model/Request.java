package com.dennisjonsson.cst.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request {
	private String user, id;
	
	public String content;
	public Date date;
	public ArrayList<String> tags;
	
	public Request(String id, String user){
		this.id = id;
		this.user = user;
		tags = new ArrayList<String>();
	}
	
	public String getId(){
		return id;
	}
	
	
	public String getUser(){
		return user;
	}
	
}
