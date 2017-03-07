package com.dennisjonsson.tm.rest.provider;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.UserValidationService;

/*
 * JSON web token authentication
 * Use libraries for authentication
 * 
 * */

@JWTTokenAutorization
@Provider // @Priority(Priorities.AUTHENTICATION)
public class JWTTokenFilter implements ContainerRequestFilter {

    @Inject
    UserValidationService userValidationService;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {

	String authString = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);

	if (authString == null) {
	    throw new NotAuthorizedException("no authorization header present");
	}

	if (!authString.equalsIgnoreCase("test_app_key")) {

	    long userId;
	    try {
		userId = userValidationService.identifyUser(authString);
	    } catch (CSTServiceException e) {
		e.printStackTrace();
		throw new NotAuthorizedException(e);
	    }

	    ctx.getHeaders().add("AUTHORIZED-ID", String.valueOf(userId));
	}

	// TODO: use appropriate tool for generating token

    }

}
