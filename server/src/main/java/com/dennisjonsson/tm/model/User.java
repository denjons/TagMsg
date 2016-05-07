package com.dennisjonsson.tm.model;

import java.util.Date;


public class User {
	
	private String id;
	public Date date;
	
	public User(String id) {
		super();
		this.id = id;
	}
	
	

	public User(String id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}
	



	public String getId() {
		return id;
	}
	
	
}
