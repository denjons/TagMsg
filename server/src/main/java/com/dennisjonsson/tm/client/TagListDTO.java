package com.dennisjonsson.tm.client;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class TagListDTO {
	
	@Min(1)
	public int limit;
	
	@Min(0)
	public int offset;
	
	@NotNull
	@NotEmpty
	public ArrayList<String> tags;
	
	public String startDate;
	
	public TagListDTO(){
		
	}
	
	public TagListDTO(int limit, int offset, ArrayList<String> tags) {
		this.limit = limit;
		this.offset = offset;
		this.tags = tags;
	}

	public TagListDTO(int limit, int offset, ArrayList<String> tags, String startDate) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.tags = tags;
		this.startDate = startDate;
	}
	
	

	
}
