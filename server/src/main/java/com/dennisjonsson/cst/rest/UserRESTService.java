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

import com.dennisjonsson.cst.client.UserDTO;
import com.dennisjonsson.cst.client.UserTagDTO;
import com.dennisjonsson.cst.data.UserTransformer;
import com.dennisjonsson.cst.model.User;
import com.dennisjonsson.cst.service.CSTServiceException;
import com.dennisjonsson.cst.service.UserService;



@Path("/user")
@RequestScoped
public class UserRESTService {
	
	@Inject
	Validator validator;
	
	@Inject
	UserService UserCreationService;

	
	@POST
	@Path("/adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO postAddUser(UserDTO userDto){
		
		User user = null;

		try{
			validateUser(userDto);
			user = UserTransformer.toUser(userDto);
			UserCreationService.addUser(user);
			
			UserDTO newUser = UserTransformer.toUserDTO(user);
			return newUser;
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
	@Path("/addusertags")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAddUserTags(UserTagDTO userDto){
		
		Response.ResponseBuilder builder = null;

		try{
			
			validateUserTag(userDto);
			UserCreationService.addTagForUser(userDto.tags, userDto.user.id);
			
			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = createViolationResponse(e.getConstraintViolations());
		} catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
		
	}
	
	@POST
	@Path("/removeusertags")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRemoveTagForUser(UserTagDTO userDto){
		
		Response.ResponseBuilder builder = null;

		try{
			
			validateUserTag(userDto);
			UserCreationService.removeTagForUser(userDto.tags, userDto.user.id);

			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = createViolationResponse(e.getConstraintViolations());
		} catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
		
	}
	
	private void validateUserTag(UserTagDTO user) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<UserTagDTO>> violations = validator.validate(user);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	private void validateUser(UserDTO user) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
	
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
}
