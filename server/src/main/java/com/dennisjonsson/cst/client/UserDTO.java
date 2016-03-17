package com.dennisjonsson.cst.client;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {
	
	@NotNull
	public String id;
	
	@NotNull
	public String appKey;
	
	public String date;
	
	public UserDTO(){}

	public UserDTO(String id, String date) {
		super();
		this.id = id;
		this.date = date;
	}

	public UserDTO(String id, String appKey, String date) {
		super();
		this.id = id;
		this.appKey = appKey;
		this.date = date;
	}
	
	
	
}
