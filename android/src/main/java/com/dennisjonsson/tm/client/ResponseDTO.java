package com.dennisjonsson.tm.client;




public class ResponseDTO {
	
	/*
	 *  private String id, user, request;
		public String content;
		public Date date;
	 * */
	


	public String id;

	public UserDTO user;

	public String request;

	public String content;
	
	public String date;

	public ResponseDTO(String id, UserDTO user, String request, String content, String date) {
		this.id = id;
		this.user = user;
		this.request = request;
		this.content = content;
		this.date = date;
	}

	public ResponseDTO() {
		super();
	}
	
	
	
	
	

}
