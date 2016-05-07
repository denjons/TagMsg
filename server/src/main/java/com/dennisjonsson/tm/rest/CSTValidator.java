package com.dennisjonsson.tm.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import com.dennisjonsson.tm.client.NewUserDTO;
import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.RequestUpdateDTO;
import com.dennisjonsson.tm.client.TagsDTO;
import com.dennisjonsson.tm.client.ResponseDTO;
import com.dennisjonsson.tm.client.ResponseUpdateDTO;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.client.UserTagDTO;

@RequestScoped
public class CSTValidator {
	
	@Inject
	Validator validator;
	
	void validateResponse(ResponseDTO responseDTO) throws ConstraintViolationException, ValidationException {
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
	
	void validateRequestsUpdate(RequestUpdateDTO request){
		Set<ConstraintViolation<RequestUpdateDTO>> violations = validator.validate(request);
		
		if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	}
	
	void validateResponseUpdateDTO(ResponseUpdateDTO response){
		Set<ConstraintViolation<ResponseUpdateDTO>> violations = validator.validate(response);
		
		if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	}
	
	void validateTags(TagsDTO request){
		Set<ConstraintViolation<TagsDTO>> violations = validator.validate(request);
		
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
	
	void validateNewUserDTO(NewUserDTO user) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<NewUserDTO>> violations = validator.validate(user);
	
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
