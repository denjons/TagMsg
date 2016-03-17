package com.dennisjonsson.cst.client;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseDTO {
	
	/*
	 *  private String id, user, request;
		public String content;
		public Date date;
	 * */
	
	@NotNull
	public String appKey;
	
	@NotNull
	public String id;
	
	@NotNull
	public String user;
	
	@NotNull
	public String request;
	
	@NotNull
	public String content;
	
	public String date;

	public ResponseDTO(String id, String user, String request, String content, String date) {
		super();
		this.id = id;
		this.user = user;
		this.request = request;
		this.content = content;
		this.date = date;
	}

	public ResponseDTO() {
		super();
	}
	
	
	
	
	

}
