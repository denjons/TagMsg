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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dennisjonsson.tm.client.NewUserDTO;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.client.UserTagDTO;
import com.dennisjonsson.tm.data.UserTransformer;
import com.dennisjonsson.tm.model.User;
import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.UserService;



@Path("/user")
@RequestScoped
public class UserRESTService {
	
	@Inject
	CSTValidator validator;
	
	@Inject
	UserService UserCreationService;

	
	@POST
	@Path("/adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO postAddUser(NewUserDTO userDto){
		
		try{
			validator.validateNewUserDTO(userDto);
			User user = UserCreationService.addUser();
			UserDTO newUser = UserTransformer.toUserDTO(user);
			return newUser;
		}
		catch(ConstraintViolationException e){
			Response.ResponseBuilder builder = 
					validator.createViolationResponse(e.getConstraintViolations());
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
			
			validator.validateUserTag(userDto);
			UserCreationService.addTagForUser(userDto.tags, userDto.user.id);
			
			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = validator.createViolationResponse(e.getConstraintViolations());
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
			validator.validateUser(userDto.user);
			validator.validateUserTag(userDto);
			UserCreationService.removeTagForUser(userDto.tags, userDto.user.id);

			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = validator.createViolationResponse(e.getConstraintViolations());
		} catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
		
	}
	

}
