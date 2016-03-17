package com.dennisjonsson.cst.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/test")
@RequestScoped
public class TestRESTService {
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String pingIt(){
		return "ping";
	}
	
}
