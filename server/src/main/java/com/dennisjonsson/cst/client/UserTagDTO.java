package com.dennisjonsson.cst.client;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class UserTagDTO {
	
	@NotNull
	public UserDTO user; 
	
	@NotNull
	@NotEmpty
	public ArrayList<String> tags;
}
