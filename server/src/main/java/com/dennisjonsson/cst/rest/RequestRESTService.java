package com.dennisjonsson.cst.rest;

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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dennisjonsson.cst.client.RequestDTO;
import com.dennisjonsson.cst.client.RequestListDTO;
import com.dennisjonsson.cst.client.UserDTO;
import com.dennisjonsson.cst.data.RequestTransformer;
import com.dennisjonsson.cst.data.UserTransformer;
import com.dennisjonsson.cst.model.Request;
import com.dennisjonsson.cst.model.User;
import com.dennisjonsson.cst.service.CSTServiceException;
import com.dennisjonsson.cst.service.RequestService;

@Path("/request")
@RequestScoped
public class RequestRESTService {
	
	@Inject
	Validator validator;
	
	@Inject
	RequestService requestService;
	
	
	@POST
	@Path("/addRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRequest(RequestDTO requestDTO){
		Response.ResponseBuilder builder = null;

		try{
			
			validateRequest(requestDTO);
			Request request = RequestTransformer.toRequest(requestDTO);
			requestService.addRequest(request);
			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = createViolationResponse(e.getConstraintViolations());
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
	}
	
	@POST
	@Path("/getRequestsForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestListDTO postGetRequestsForUser(UserDTO userDTO) {
		
		try{
			validateUser(userDTO);
			User user = UserTransformer.toUser(userDTO);
			RequestListDTO dto = RequestTransformer.toRequestListDTO(requestService.getRequests(user));
			return dto;
		}
		catch(ConstraintViolationException e){
			Response.ResponseBuilder builder = 
					createViolationResponse(e.getConstraintViolations());
			throw new WebApplicationException(builder.build());
			
		}
		catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}
	
	@POST
	@Path("/getEligibleRequestsForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestListDTO postGetEligibleRequestsForUser(UserDTO userDTO){
		
		try{
			validateUser(userDTO);
			User user = UserTransformer.toUser(userDTO);
			RequestListDTO dto = RequestTransformer.toRequestListDTO(requestService.getEligibleRequestsForUser(user));
			return dto;
		}
		catch(ConstraintViolationException e){
			Response.ResponseBuilder builder = 
					createViolationResponse(e.getConstraintViolations());
			throw new WebApplicationException(builder.build());
			
		}
		catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}
	
	
	private void validateRequest(RequestDTO requestDTO) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<RequestDTO>> violations = validator.validate(requestDTO);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
      //  log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
	
	private void validateUser(UserDTO user) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
}
