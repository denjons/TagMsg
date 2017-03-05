package com.dennisjonsson.tm.service;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotAuthorizedException;

import com.dennisjonsson.tm.util.JWTToken;

@ApplicationScoped
public class UserValidationService extends DataSource {

    public String identifyUser(String auth) {

	String id = "test-user-id-123456789123456789";

	HashMap<String, Object> claims = JWTToken.parseJWT(auth);

	// do database lookups or whatever
	if (!claims.get(JWTToken.ID).toString().equals(id)) {
	    throw new NotAuthorizedException("invalid app key: " + "'" + claims.get(JWTToken.ID) + "'");
	}

	return claims.get(JWTToken.ID).toString();
    }
}
