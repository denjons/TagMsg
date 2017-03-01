package com.dennisjonsson.tm.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.dennisjonsson.tm.client.RequestUpdateDTO;
import com.dennisjonsson.tm.data.CSTDatabase;
import com.dennisjonsson.tm.data.UserTransformer;
import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.model.Request;

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
	
	public ArrayList<Request> getRequests(RequestUpdateDTO requestUpdateDTO) throws CSTServiceException{
		ArrayList<Request> list = null;
		try{
			list = database.getRequestsFromUser(
					requestUpdateDTO.user.id,
					requestUpdateDTO.limit,
					requestUpdateDTO.offset,
					requestUpdateDTO.fromRequest,
					requestUpdateDTO.beforeRequest);
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
		return list;
	}
	
	public ArrayList<Request> getEligibleRequestsForUser(RequestUpdateDTO requestDTO) throws CSTServiceException{
		ArrayList<Request> list = null;
		try{
			User user = UserTransformer.toUser(requestDTO.user);
			list = database.getEligibleRequests(user.getUu_id(), requestDTO.limit, requestDTO.offset, 
					requestDTO.fromRequest, requestDTO.beforeRequest);
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState()+": "+e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
		return list;
	}
	
	public ArrayList<Request> getRequestsFromTags(ArrayList<String> tags, int limit, int offset,
			String startDate, String endDate) throws CSTServiceException{
		ArrayList<Request> list = null;
		try{
			
			list = database.getRequestsFromTags(
					tags, 
					limit, 
					offset,
					startDate, 
					endDate);
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState()+": "+e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
		return list;
	}
	
	

}
