package com.dennisjonsson.tm.client;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseListDTO {
	
	@NotNull
	public ArrayList<ResponseDTO> responses;

	public ResponseListDTO(ArrayList<ResponseDTO> responses) {
		super();
		this.responses = responses;
	}

	public ResponseListDTO() {
		super();
	}
	
	
}
