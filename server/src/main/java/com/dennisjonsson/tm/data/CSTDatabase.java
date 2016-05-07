package com.dennisjonsson.tm.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;

import com.dennisjonsson.tm.model.*;
import com.dennisjonsson.tm.util.SQLUtil;
	
@ApplicationScoped
public class CSTDatabase{
	
	private String constring;
	
	private Connection conn = null;
	//private Statement stmt = null;
	// ResultSet rs = null;
	String DB_HOST;
	String DB_PORT;
	String DB_USER;
	String DB_PASSWORD;
	String DB;
	
	public CSTDatabase(){
		
		DB_HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		DB_PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		DB_USER = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		DB_PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
		DB = "tm_db";
		
		
		this.constring = 
				"jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+DB+"?"
				+ "user="+DB_USER+"&"
				+ "password="+DB_PASSWORD;
				
		
		//this.constring = "jdbc:mysql://127.0.0.1:3306/tm_db?user=dennis&password=b0c136a4";
	}
	
	
	// add user
	//create procedure addUser(id VARCHAR(40), OUT out_oaram int)
	public User addUser() throws SQLException{

		CallableStatement cStmt = null;
		ResultSet rs = null;
		User user = null;
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call addUser()}");
			
			boolean hadResults = cStmt.execute();
			
			if (hadResults) {
				rs = cStmt.getResultSet();	
				if(rs.next()){
					user = resultSetToUser(rs);
				}
			}
			
			return user;
			
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
			
			if(conn!=null){
				conn.close();
				conn = null;
			}
		}
	}
	
	/*
	 * CREATE TABLE IF NOT EXISTS Users (
	id VARCHAR(40),
    last_update timestamp default current_timestamp on update current_timestamp,
	date datetime,
	PRIMARY KEY (id)
);
	 * */
	private User resultSetToUser(ResultSet rs) throws SQLException{
		final String PARAM_USER_ID = "id";
		final String PARAM_DATE = "date";
		
		User user = new User(rs.getString(PARAM_USER_ID),rs.getTimestamp(PARAM_DATE));
		return user;
		
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
			if(conn!=null){
				conn.close();
				conn = null;
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
			
			if(conn!=null){
				conn.close();
				conn = null;
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
			
			if(conn!=null){
				conn.close();
				conn = null;
			}
		}
		
	}
	
	
	// get requests from user
	// getRequestsFromUser(userId VARCHAR(40), requestLimit int, requestOffset int, 
	// fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
	public ArrayList<Request> getRequestsFromUser(String user , int limit, int offset, 
			String startDate, String endDate) throws SQLException, ParseException{
		
		String PARAM_USER = "userId";
		String PARAM_LIMIT = "requestLimit";
		String PARAM_OFFSET = "requestOffset";
		String PARAM_START_DATE = "fromRequest";
		String PARAM_END_DATE = "beforeRequest";
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call getRequestsFromUser(?,?,?,?,?)}");
			
			cStmt.setString(PARAM_USER, user);
			cStmt.setInt(PARAM_LIMIT, limit);
			cStmt.setInt(PARAM_OFFSET, offset);
			cStmt.setString(PARAM_START_DATE, startDate);
			cStmt.setString(PARAM_END_DATE, endDate);
			
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
			
			if(conn!=null){
				conn.close();
				conn = null;
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
		
			//DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
			request.date = rs.getString("date");
			
			request.content = rs.getString("content");
			String tags = rs.getString("tags");

			String [] tagsArray = tags.split("'");
			
			for(int i = 0; i < tagsArray.length; i++){
				request.tags.add(tagsArray[i]);
			}
			
			return request;
	}
	
	// get requests a user is eligible for
	//getEligibleRequestsForUser(userId VARCHAR(40), requestLimit int, requestOffset int, 
	// fromRequest VARCHAR(40), beforeRequest VARCHAR(40))
	public ArrayList<Request> getEligibleRequests(String user, int limit, int offset, 
			String startDate, String endDate) throws SQLException, ParseException{
		
		String PARAM_USER = "userId";
		String PARAM_LIMIT = "requestLimit";
		String PARAM_OFFSET = "requestOffset";
		String PARAM_START_DATE = "fromRequest";
		String PARAM_END_DATE = "beforeRequest";
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		if(conn == null)
			conn = DriverManager.getConnection(constring);
		
		try{
			cStmt = conn.prepareCall("{call getEligibleRequestsForUser(?, ?, ?, ?, ?)}");
			
			cStmt.setString(PARAM_USER, user);
			cStmt.setInt(PARAM_LIMIT, limit);
			cStmt.setInt(PARAM_OFFSET, offset);
			cStmt.setString(PARAM_START_DATE, startDate);
			cStmt.setString(PARAM_END_DATE, endDate);

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
			
			if(conn!=null){
				conn.close();
				conn = null;
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
			
			if(conn!=null){
				conn.close();
				conn = null;
			}
		
		}
		
	}
	
	// getResponsesForRequest
	//getResponsesForRequest(user VARCHAR(40), reqId varchar(40), responseLimit int, responseOffset int, 
	// fromResponse VARCHAR(40), beforeResponse VARCHAR(40))
	/*
		NOTE: the user have to have made the request in order to see all the responses
	*/
		public ArrayList<Response> getResponsesForRequest(String user, String request, 
				int limit, int offset, String startDate, String endDate) throws SQLException, ParseException{
		
		String PARAM_USER = "user";
		String PARAM_REQUEST = "reqId";
		String PARAM_LIMIT = "responseLimit";
		String PARAM_OFFSET = "responseOffset";
		String PARAM_START_DATE = "fromResponse";
		String PARAM_END_DATE = "beforeResponse";
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			cStmt = conn.prepareCall("{call getResponsesForRequest(?,?,?,?,?,?)}");
			
			cStmt.setString(PARAM_USER, user);
			cStmt.setString(PARAM_REQUEST, request);
			
			cStmt.setInt(PARAM_LIMIT, limit);
			cStmt.setInt(PARAM_OFFSET, offset);
			cStmt.setString(PARAM_START_DATE, startDate);
			cStmt.setString(PARAM_END_DATE, endDate);
			
		
			
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
			
			if(conn!=null){
				conn.close();
				conn = null;
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
	
	/*
	 * procedure getRequestsFromTags(tags TEXT, requestLimit int, requestOffset int, startDate TimeStamp, endDate Timestamp)
	 * */
	public ArrayList<Request> getRequestsFromTags(ArrayList<String> tags, int limit, int offset,
			String startDate, String endDate) 
			throws SQLException, ParseException{
		
		CallableStatement cStmt = null;
		ResultSet rs = null;
		
		final String PARAM_TAGS = "tags";
		final String PARAM_LIMIT = "requestLimit";
		final String PARAM_OFFSET = "requestOffset";
		String PARAM_START_DATE = "fromRequest";
		String PARAM_END_DATE = "beforeRequest";
		
		try{
			if(conn == null)
				conn = DriverManager.getConnection(constring);
			
			
			cStmt = conn.prepareCall("{call getRequestsFromTags(?,?,?,?,?)}");
		
			
			String tagsString = Arrays.toString(tags.toArray());
			tagsString = tagsString.substring(1, tagsString.length() - 1);
			
			cStmt.setString(PARAM_TAGS, tagsString);
			cStmt.setInt(PARAM_LIMIT, limit);
			cStmt.setInt(PARAM_OFFSET, offset);
			cStmt.setString(PARAM_END_DATE, endDate);
			cStmt.setString(PARAM_START_DATE, startDate);

			
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
			
			if(conn!=null){
				conn.close();
				conn = null;
			}
		}
		
	}
	
	
	
	
	
}

