package com.dennisjonsson.cst.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.dennisjonsson.cst.data.CSTDatabase;
import com.dennisjonsson.cst.model.User;
import com.dennisjonsson.cst.util.SQLUtil;

@ApplicationScoped
public class UserService {
	@Inject
	CSTDatabase database;
	
	public void addUser(User user) throws CSTServiceException{
		try {
			Date date = database.addUser(user);
			user.date = date;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
			
		}
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
