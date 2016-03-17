package com.dennisjonsson.cst.client;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RequestDTO {
	
	@NotNull
	public String appKey;
	
	@NotNull
	public String id;
	
	@NotNull
	public String user;
	
	@NotNull
	public String content;
	
	@NotNull
	public ArrayList<String> tags;
	
	public String date;
	
	

	public RequestDTO(String appKey, String id, String user, String content, ArrayList<String> tags, String date) {
		super();
		this.appKey = appKey;
		this.id = id;
		this.user = user;
		this.content = content;
		this.tags = tags;
		this.date = date;
	}

	public RequestDTO(String appKey, String id, String user, String content) {
		super();
		this.appKey = appKey;
		this.id = id;
		this.user = user;
		this.content = content;
	}

	public RequestDTO(String appKey, String id, String user, String content, String date) {
		super();
		this.appKey = appKey;
		this.id = id;
		this.user = user;
		this.content = content;
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
 