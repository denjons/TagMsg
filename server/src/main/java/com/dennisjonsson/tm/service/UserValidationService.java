package com.dennisjonsson.tm.service;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotAuthorizedException;

import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.util.JWTToken;

@ApplicationScoped
public class UserValidationService extends DataSource {

    public int identifyUser(String auth) throws CSTServiceException {

	HashMap<String, Object> claims = JWTToken.parseJWT(auth);

	int id = Integer.parseInt(claims.get(JWTToken.ID).toString());
	// do database lookups or whatever
	if (!checkUser(id)) {
	    throw new NotAuthorizedException("invalid app key: " + "'" + claims.get(JWTToken.ID) + "'");
	}

	return id;
    }

    private boolean checkUser(int userId) throws CSTServiceException {
	beginTransaction();

	User user = em.find(User.class, userId);

	endTransaction();

	return user != null;

    }
}
