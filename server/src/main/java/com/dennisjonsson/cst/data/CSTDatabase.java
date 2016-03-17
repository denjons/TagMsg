package com.dennisjonsson.cst.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import com.dennisjonsson.cst.model.*;
import com.dennisjonsson.cst.util.SQLUtil;
	
@ApplicationScoped
public class CSTDatabase{
	
	private String constring;
	
	private Connection conn = null;
	//private Statement stmt = null;
	// ResultSet rs = null;
	
	public CSTDatabase(){
		this.constring = "jdbc:mysql://127.0.0.1:3306/db_cst?user=dennis&password=b0c136a4";
	}
	
	
	// add user
	//create procedure addUser(id VARCHAR(40), OUT out_oaram int)
	public Date addUser(User user) throws SQLException{
		
		String userId = user.getId();
		
		
		
		final String PARAM_USER_ID = "id";
		final String PARAM_DATE = "date";
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call addUser(?)}");
			cStmt.setString(PARAM_USER_ID, userId);
			
			Date date = null;
			boolean hadResults = cStmt.execute();
			
			while (hadResults) {
				rs = cStmt.getResultSet();	
				while(rs.next()){
					date = rs.getTimestamp(PARAM_DATE);
				}
				hadResults = cStmt.getMoreResults();
			}
			
			return date;
			
		}finally{
		// close
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { } // ignore
	
				rs = null;
			}
			
			// close
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
		
	}
	
	// add tag for user
	// create procedure addTagForUser(user int, tag_name VARCHAR(50))
	public void addTagForUser(String user, String tag) throws SQLException{
		
		String PARAM_USER = "user";
		String PARAM_TAG = "tags";
		
		CallableStatement cStmt = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call addTagForUser(?,?)}");
			cStmt.setString(PARAM_USER, user);
			cStmt.setString(PARAM_TAG, tag);
			
			cStmt.execute();
		}finally{
			// close
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
	}
	
	// remove tag for user
	// create procedure RemoveTagForUser(id int, tag_name VARCHAR(50))
	public void removeTagForUser(String user, String tag) throws SQLException{
		
		String PARAM_USER = "id";
		String PARAM_TAG = "tags";
		
		CallableStatement cStmt = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call RemoveTagForUser(?,?)}");
			cStmt.setString(PARAM_USER, user);
			cStmt.setString(PARAM_TAG, tag);
			
			cStmt.execute();
		}finally{
			// close
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
	}
	
	// add request
	// create procedure addRequest(user_id VARCHAR(40), content_input TEXT, req_id VARCHAR(40), tags VARCHAR(250))
	public void addRequest(Request req) throws SQLException{
		
		String PARAM_USER = "user_id";
		String PARAM_CONTENT = "content_input";
		String PARAM_REQUEST = "req_id";
		String PARAM_TAGS = "tags";
		
		CallableStatement cStmt = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call addRequest(?,?,?,?)}");
			
			cStmt.setString(PARAM_USER, req.getUser());
			cStmt.setString(PARAM_CONTENT, req.content);
			cStmt.setString(PARAM_REQUEST, req.getId());
			cStmt.setString(PARAM_TAGS, SQLUtil.ListToString(req.tags));
			
			cStmt.execute();
		
		}finally{
		
			// close
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
	}
	
	
	// get requests from user
	// create procedure getRequestsFromUser(userId int)
	public ArrayList<Request> getRequestsFromUser(String user) throws SQLException, ParseException{
		
		String PARAM_USER = "userId";
	
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call getRequestsFromUser(?)}");
			
			cStmt.setString(PARAM_USER, user);
			
			ArrayList<Request> result = new ArrayList<Request>();
			
			boolean hadResults = cStmt.execute();
			
			while (hadResults) {
				rs = cStmt.getResultSet();
				while(rs.next()){
						result.add(ResultSetToRequest(rs));
				}
				hadResults = cStmt.getMoreResults();
			}
			return result;
		}
		finally{
		// close
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { } // ignore
	
				rs = null;
			}
			
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
	}
	
	/*
	CREATE TABLE IF NOT EXISTS Requests(
	id VARCHAR(40),
	user int,
	content TEXT,
	PRIMARY KEY(id),
    date timestamp default current_timestamp,
	FOREIGN KEY (user) REFERENCES Users(id) 
); 

create or replace view getRequests as
	select ReqTable.id as id, ReqTable.user, ReqTable.content, tags, date from
	*/
	private Request ResultSetToRequest(ResultSet rs)throws SQLException, ParseException{
		
		Request request = 
			new Request(rs.getString("id"), rs.getString("user") );
		
			DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
			request.date = format.parse(rs.getString("date"));
			
			request.content = rs.getString("content");
			String tags = rs.getString("tags");

			String [] tagsArray = tags.split("'");
			
			for(int i = 0; i < tagsArray.length; i++){
				request.tags.add(tagsArray[i]);
			}
			
			return request;
	}
	
	// get requests a user is eligible for
	// create procedure getEligibleRequestsForUSer(userId VARCHAR(40))
	public ArrayList<Request> getEligibleRequests(String user) throws SQLException, ParseException{
		
		String PARAM_USER = "userId";
	
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		if(conn == null)
			conn = DriverManager.getConnection(constring);
		
		try{
			cStmt = conn.prepareCall("{call getEligibleRequestsForUser(?)}");
			
			cStmt.setString(PARAM_USER, user);
			
			ArrayList<Request> result = new ArrayList<Request>();
			
			boolean hadResults = cStmt.execute();
			
			while (hadResults) {
				rs = cStmt.getResultSet();
				while(rs.next()){
						result.add(ResultSetToRequest(rs));
				}
				hadResults = cStmt.getMoreResults();
			}
			return result;
		}
		finally{
		// close
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { } // ignore
	
				rs = null;
			}
			
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
		
	}
	
	// add or update response to request
	// create procedure addResponseToRequest(responder VARCHAR(40), resp_id varchar(40), resp_content TEXT, req_id varchar(40))
	public void addResponse(Response response) throws SQLException{
		
		String PARAM_USER = "responder";
		String PARAM_RESPONSE = "resp_id";
		String PARAM_CONTENT = "resp_content";
		String PARAM_REQUEST = "req_id";
		
		CallableStatement cStmt = null;
		
		try{
			
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call addResponseToRequest(?,?,?,?)}");
			
			cStmt.setString(PARAM_RESPONSE, response.getId());
			cStmt.setString(PARAM_REQUEST, response.getRequest());
			cStmt.setString(PARAM_USER, response.getUser());
			cStmt.setString(PARAM_CONTENT, response.content);
			
			cStmt.execute();
		
		}finally{
			// close
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		
		}
		
	}
	
	// getResponsesForRequest
	//create procedure getResponsesForRequest(user VARCHAR(40), reqId varchar(40))
	/*
		NOTE: the user have to have made the request in order to see all the responses
	*/
		public ArrayList<Response> getResponsesForRequest(Request request) throws SQLException, ParseException{
		
		String PARAM_USER = "user";
		String PARAM_REQUEST = "reqId";
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call getResponsesForRequest(?,?)}");
			
			cStmt.setString(PARAM_USER, request.getUser());
			cStmt.setString(PARAM_REQUEST, request.getId());
			
			ArrayList<Response> result = new ArrayList<Response>();
			
			boolean hadResults = cStmt.execute();
			
			while (hadResults) {
				rs = cStmt.getResultSet();
				while(rs.next()){
						result.add(ResultSetToResponse(rs));
				}
				hadResults = cStmt.getMoreResults();
			}
			
			return result;
		
		}finally{
			// close
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { } // ignore
	
				rs = null;
			}
			
			if(cStmt != null) {
				try {
					cStmt.close();
				} catch (SQLException sqlEx) { } // ignore
				cStmt = null;
			}
		}
		
		
	}
	
	/*
	CREATE TABLE IF NOT EXISTS Responses(
		id VARCHAR(40),
		user VARCHAR(40),
		request VARCHAR(40),
		date timestamp default current_timestamp,
		content TEXT,
		PRIMARY KEY(id),
		FOREIGN KEY (user) REFERENCES Users(id),
		FOREIGN KEY (request) REFERENCES Requests(id)
	);
	*/
	private Response ResultSetToResponse(ResultSet rs)throws SQLException, ParseException{
		
		DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		Date date = format.parse(rs.getString("date"));
		Response response = 
			new Response(rs.getString("id"), rs.getString("user"),rs.getString("request"),date);
			response.content = rs.getString("content");

			return response;
	}
	
	
	
	
	
}

