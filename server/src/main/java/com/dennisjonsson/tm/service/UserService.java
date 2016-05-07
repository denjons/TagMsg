package com.dennisjonsson.tm.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.dennisjonsson.tm.data.CSTDatabase;
import com.dennisjonsson.tm.model.User;
import com.dennisjonsson.tm.util.SQLUtil;

@ApplicationScoped
public class UserService {
	@Inject
	CSTDatabase database;
	
	public User addUser() throws CSTServiceException{
		User user = null;
		try {
			user = database.addUser();
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState()+", "+e.getMessage());
			
		}
		return user;
	}
	
	public void addTagForUser(ArrayList<String> tags, String userId) throws CSTServiceException{
		
		String tagList = SQLUtil.ListToString(tags);
	
		try {
			database.addTagForUser(userId, tagList);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		}

	}
	
	public void removeTagForUser(ArrayList<String> tags, String userId) throws CSTServiceException{
		String tagList = SQLUtil.ListToString(tags);
		
		try {
			database.removeTagForUser(userId, tagList);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		}
	}
}
