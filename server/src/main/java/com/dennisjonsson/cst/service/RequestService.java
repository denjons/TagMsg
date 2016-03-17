package com.dennisjonsson.cst.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.dennisjonsson.cst.data.CSTDatabase;
import com.dennisjonsson.cst.model.Request;
import com.dennisjonsson.cst.model.User;

@ApplicationScoped
public class RequestService {
	
	@Inject
	CSTDatabase database;
	
	public void addRequest(Request request) throws Exception{
		try {
			database.addRequest(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState()+": "+e.getMessage());
		}
	}
	
	public ArrayList<Request> getRequests(User user) throws CSTServiceException{
		ArrayList<Request> list = null;
		try{
			list = database.getRequestsFromUser(user.getId());
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
		return list;
	}
	
	public ArrayList<Request> getEligibleRequestsForUser(User user) throws CSTServiceException{
		ArrayList<Request> list = null;
		try{
			list = database.getEligibleRequests(user.getId());
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
		return list;
	}
	

}
