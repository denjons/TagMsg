package com.dennisjonsson.cst.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.dennisjonsson.cst.client.UserDTO;
import com.dennisjonsson.cst.model.User;

public class UserTransformer {
	
	public static User toUser(UserDTO userDto){
		
		User user = new User(userDto.id);
		DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		
		String date = userDto.date;
		try {
			if(date != null)
				user.date = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static UserDTO toUserDTO(User user){
		return new UserDTO(user.getId(), user.date.toString());
		
	}

}
