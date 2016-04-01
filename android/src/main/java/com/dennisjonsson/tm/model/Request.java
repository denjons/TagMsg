package com.dennisjonsson.tm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class Request {

	public final String id, user;
	public final String content;
	public final String date;
	public final ArrayList<String> tags;

	public Request(String id, String user, String content, String date, ArrayList<String> tags) {
		this.id = id;
		this.user = user;
		this.content = content;
		this.date = date;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return id+"; "+user+"; "+content+"; "+date+"; "+ Arrays.toString(tags.toArray());
	}
}
