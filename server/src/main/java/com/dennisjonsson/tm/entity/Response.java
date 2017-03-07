package com.dennisjonsson.tm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;


@NamedStoredProcedureQuery(name = "getResponsesForRequest", procedureName = "GET_RESPONSES_FOR_REQUESTS", resultClasses = {
		EligibleRequestsResult.class })
@Entity
public class Response {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "uu_id")
	private String uu_id;
	
	@Column(name = "request")
	private int request;
	
	@Column(name = "content")
	private String content;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "date")
    private Date date;
	
	public Response() {
		super();
	}
	
	

	public Response( String uu_id,  int request, String content) {
		super();
		this.uu_id = uu_id;
		this.request = request;
		this.content = content;
	}

	

	public Response(String uu_id, int request, String content, Date date) {
		super();
		this.uu_id = uu_id;
		this.request = request;
		this.content = content;
		this.date = date;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUu_id() {
		return uu_id;
	}

	public void setUu_id(String uu_id) {
		this.uu_id = uu_id;
	}

	public int getRequest() {
		return request;
	}

	public void setRequest(int request) {
		this.request = request;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	

	
}
