package com.dennisjonsson.tm.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.dennisjonsson.tm.application.TMAppConstants;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.model.User;

public class UserTransformer {
	
	public static User toUser(UserDTO userDto){
		
		User user = new User(userDto.id);
		//DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		
		return user;
	}
	
	public static UserDTO toUserDTO(User user){
		return new UserDTO(user.getId(),TMAppConstants.WILDFLY_APP_KEY);
	}

}
