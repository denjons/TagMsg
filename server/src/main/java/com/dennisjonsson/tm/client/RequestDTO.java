package com.dennisjonsson.tm.client;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestDTO {

    @NotNull
    public String id;

    @NotNull
    public String content;

    @NotNull
    public ArrayList<String> tags;

    public String date;

    public RequestDTO(String id, String content, ArrayList<String> tags, String date) {
	super();
	this.id = id;
	this.content = content;
	this.tags = tags;
	this.date = date;
    }

    public RequestDTO() {
	super();
    }

}

/*
 * CREATE TABLE IF NOT EXISTS Requests( id VARCHAR(40), user VARCHAR(40),
 * content TEXT, PRIMARY KEY(id), date timestamp default current_timestamp,
 * FOREIGN KEY (user) REFERENCES Users(id) );
 */
