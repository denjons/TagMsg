package com.dennisjonsson.tm.client;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.dennisjonsson.tm.application.TMAppConstants;

@XmlRootElement
public class UserDTO {
	
	@NotNull
	public String id;
	
	@NotNull
	@Pattern(regexp = TMAppConstants.ANDROID_APP_KEY)
	public String appKey;
	
	public UserDTO(){}

	public UserDTO(String id, String appKey) {
		super();
		this.id = id;
		this.appKey = appKey;
	}
	
	
	
}
