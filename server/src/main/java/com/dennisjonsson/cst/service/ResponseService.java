package com.dennisjonsson.cst.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.dennisjonsson.cst.data.CSTDatabase;
import com.dennisjonsson.cst.model.Request;
import com.dennisjonsson.cst.model.Response;

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
	
	public ArrayList<Response> getResponsesForRequest(Request request) throws CSTServiceException{
		
		try {
			return database.getResponsesForRequest(request);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getSQLState());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
	}
}
