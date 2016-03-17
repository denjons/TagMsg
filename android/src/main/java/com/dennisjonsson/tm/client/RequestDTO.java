package com.dennisjonsson.tm.client;

import java.util.ArrayList;


public class RequestDTO {


	public String id;

	public UserDTO user;

	public String content;

	public ArrayList<String> tags;
	
	public String date;


	public RequestDTO(String id, UserDTO user, String content, ArrayList<String> tags, String date) {
		this.id = id;
		this.user = user;
		this.content = content;
		this.tags = tags;
		this.date = date;
	}

	public RequestDTO() {
		super();
	}
	
	
}

/*
 * CREATE TABLE IF NOT EXISTS Requests(
	id VARCHAR(40),
	user VARCHAR(40),
	content TEXT,
	PRIMARY KEY(id),
    date timestamp default current_timestamp,
	FOREIGN KEY (user) REFERENCES Users(id) 
); 
 * */
 