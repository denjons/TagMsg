package com.dennisjonsson.tm.client;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestListDTO {

	@NotNull
	public ArrayList<RequestDTO> requests;
}
