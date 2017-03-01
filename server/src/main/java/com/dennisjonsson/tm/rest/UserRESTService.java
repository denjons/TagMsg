package com.dennisjonsson.tm.rest;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dennisjonsson.tm.application.TMAppConstants;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.client.UserTagListDTO;
import com.dennisjonsson.tm.data.UserTransformer;
import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.rest.provider.JWTTokenAuthentication;
import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.UserService;
import com.dennisjonsson.tm.util.JWTToken;



@Path("/user")
//@Api(value = "/user")
@RequestScoped
public class UserRESTService {
	
	@Inject
	TMValidator validator;
	
	@Inject
	UserService UserCreationService;

	
	@GET
	/*
	@ApiOperation(value = "Creates user", 
	    notes = "Multiple status values can be provided with comma separated strings",
	    response = UserDTO.class)
	*/
	@Path("/createuser")
	@JWTTokenAuthentication
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(){
		
		try{
			User user = UserCreationService.createUser();
			UserDTO newUser = UserTransformer.toUserDTO(user);
			
			Response.ResponseBuilder builder = Response.ok(newUser)
					.header(HttpHeaders.AUTHORIZATION, JWTToken
							.createWebToken(newUser.id, TMAppConstants.SERVER_APP_KEY, "/user/createuser"));
			return builder.build();
			
		}catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
	}
	
	@POST
	@JWTTokenAuthentication
	@Path("/addusertags")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAddUserTags(UserTagListDTO userDto){
		
		Response.ResponseBuilder builder = null;

		try{
			
			validator.validateUserTag(userDto);
			
			UserCreationService.addTagForUser(userDto);
			
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
	public Response postRemoveTagForUser(UserTagListDTO userDto){
		
		Response.ResponseBuilder builder = null;

		try{
			validator.validateUser(userDto.user);
			validator.validateUserTag(userDto);
			UserCreationService.removeTagForUser(userDto);

			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = validator.createViolationResponse(e.getConstraintViolations());
		} catch (CSTServiceException e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
		
	}
	

}
