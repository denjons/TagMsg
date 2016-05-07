package com.dennisjonsson.tm.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.dennisjonsson.tm.client.ResponseUpdateDTO;
import com.dennisjonsson.tm.data.CSTDatabase;
import com.dennisjonsson.tm.model.Request;
import com.dennisjonsson.tm.model.Response;

@ApplicationScoped
public class ResponseService {
	
	@Inject
	CSTDatabase database;
	
	public void addResponseToRequest(Response response) throws CSTServiceException{
		try {
			database.addResponse(response);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		}
	}
	
	public ArrayList<Response> getResponsesForRequest(ResponseUpdateDTO response) throws CSTServiceException{
		
		try {
			return database.getResponsesForRequest(
					response.user.id,
					response.request,
					response.limit,
					response.offset,
					response.fromResponse,
					response.beforeResponse);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
	}
}
