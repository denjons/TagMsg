package com.dennisjonsson.cst.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import com.dennisjonsson.cst.client.RequestDTO;
import com.dennisjonsson.cst.client.ResponseDTO;
import com.dennisjonsson.cst.client.UserDTO;
import com.dennisjonsson.cst.client.UserTagDTO;

@ApplicationScoped
public class CSTValidator {
	
	@Inject
	Validator validator;
	
	void validateRequest(ResponseDTO responseDTO) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<ResponseDTO>> violations = validator.validate(responseDTO);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	void validateRequest(RequestDTO requestDTO) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<RequestDTO>> violations = validator.validate(requestDTO);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	void validateUser(UserDTO user) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	void validateUserTag(UserTagDTO user) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<UserTagDTO>> violations = validator.validate(user);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
      //  log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
	
	

}
