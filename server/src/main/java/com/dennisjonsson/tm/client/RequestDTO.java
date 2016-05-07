package com.dennisjonsson.tm.client;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.dennisjonsson.tm.application.TMAppConstants;


@XmlRootElement
public class RequestDTO {
	
	
	
	@NotNull
	public String id;
	
	@NotNull
	public UserDTO user;
	
	@NotNull
	public String content;
	
	@NotNull
	public ArrayList<String> tags;
	
	public String date;
	

	public RequestDTO(String id, UserDTO user, String content, ArrayList<String> tags, String date) {
		super();
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
 