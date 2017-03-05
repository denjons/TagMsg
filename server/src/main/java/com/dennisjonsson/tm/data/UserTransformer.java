package com.dennisjonsson.tm.data;

import java.util.ArrayList;

import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.client.UserTagListDTO;
import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.entity.UserTagRelation;

public class UserTransformer {

    public static User toUser(UserDTO userDto) {

	User user = new User(userDto.id);
	// DateFormat format = new
	// SimpleDateFormat(ApplicationConstants.DATE_FORMAT);

	return user;
    }

    public static UserDTO toUserDTO(User user) {
	return new UserDTO(user.getUu_id());
    }

    public static void toUserTagRelations(ArrayList<UserTagRelation> relations, UserTagListDTO userTagDTO, String id) {

	/*
	 * userTagDTO.tags.stream() .map((tag) -> new
	 * UserTagRelation(userTagDTO.user.id, tag)) .map(relations::add);
	 */
	for (String tag : userTagDTO.tags) {
	    relations.add(new UserTagRelation(id, tag));
	}
    }

}
