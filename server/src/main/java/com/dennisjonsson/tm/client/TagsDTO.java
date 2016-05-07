package com.dennisjonsson.tm.client;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class TagsDTO {
	
	@Min(1)
	public int limit;
	
	@Min(0)
	public int offset;
	
	@NotNull
	@NotEmpty
	public ArrayList<String> tags;
	
	public String startDate;
	public String endDate;
	
	public TagsDTO(){
		
	}
	
	public TagsDTO(int limit, int offset, ArrayList<String> tags) {
		this.limit = limit;
		this.offset = offset;
		this.tags = tags;
	}

	public TagsDTO(int limit, int offset, ArrayList<String> tags, String startDate, String endDate) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.tags = tags;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	

	
}
