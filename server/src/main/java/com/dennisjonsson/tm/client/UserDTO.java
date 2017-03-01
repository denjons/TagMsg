package com.dennisjonsson.tm.client;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {
	
	@NotNull
	public String id;
	
	public UserDTO(){}

	public UserDTO(String id) {
		super();
		this.id = id;
	}
	
	
	
}
