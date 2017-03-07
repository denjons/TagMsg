package com.dennisjonsson.tm.data;

import java.util.ArrayList;

import com.dennisjonsson.tm.client.UserTagListDTO;
import com.dennisjonsson.tm.entity.UserTagRelation;

public class UserTransformer {

    public static void toUserTagRelations(ArrayList<UserTagRelation> relations, UserTagListDTO userTagDTO, String id) {

		for (String tag : userTagDTO.tags) {
		    relations.add(new UserTagRelation(id, tag));
		}
    }

}
