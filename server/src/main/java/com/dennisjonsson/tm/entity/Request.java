package com.dennisjonsson.tm.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

//@Entity
//@Table(name="Requests")
public class Request {
	
	//@NotNull
	private String id;
	
	@NotNull
	private String user;
	
	@NotNull
	private String content;
	
	//@OneToMany()
	//@JoinColumn(referencedColumnName = "id")
	List<RequestTagRelation> tags;
	
	

}
