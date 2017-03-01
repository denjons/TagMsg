package com.dennisjonsson.tm.rest.provider;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/*
 * JSON web token authentication
 * Use libraries for authentication
 * 
 * */

@JWTTokenAuthentication
@Provider //@Priority(Priorities.AUTHENTICATION)
public class JWTTokenFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		
		String authString = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		// TODO: use appropriate tool for generating token
		
		if(authString == null || !authString.equalsIgnoreCase("test_app_key")){
			throw new NotAuthorizedException("invalid app key: "+ "'"+authString+"'");
		}
		
	}
	


}
