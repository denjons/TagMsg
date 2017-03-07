package com.dennisjonsson.tm.rest;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dennisjonsson.tm.application.TMAppConstants;
import com.dennisjonsson.tm.client.UserTagListDTO;
import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.rest.provider.JWTTokenAutorization;
import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.UserService;
import com.dennisjonsson.tm.util.JWTToken;

@Path("/user")
// @Api(value = "/user")
@RequestScoped
public class UserRESTService {

    @Inject
    TMValidator validator;

    @Inject
    UserService UserCreationService;

    @GET
    /*
     * @ApiOperation(value = "Creates user", notes =
     * "Multiple status values can be provided with comma separated strings",
     * response = UserDTO.class)
     */
    @Path("/createuser")
    @JWTTokenAutorization
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser() {

	try {
	    User user = UserCreationService.createUser();

	    Response.ResponseBuilder builder = Response.ok().header(HttpHeaders.AUTHORIZATION,
		    JWTToken.createWebToken(user.getId(), TMAppConstants.SERVER_APP_KEY, "/user/createuser"));
	    return builder.build();

	} catch (CSTServiceException e) {
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}

    }

    @POST
    @JWTTokenAutorization
    @Path("/addusertags")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAddUserTags(@HeaderParam("AUTHORIZED-ID") String userId, UserTagListDTO userDto) {

	Response.ResponseBuilder builder = null;

	try {
	    validator.validateUserTag(userDto);

	    UserCreationService.addTagForUser(userDto, userId);

	    builder = Response.ok();

	} catch (ConstraintViolationException e) {
	    builder = validator.createViolationResponse(e.getConstraintViolations());
	} catch (CSTServiceException e) {
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}
	return builder.build();

    }

    @GET
    @JWTTokenAutorization
    @Path("/getusertags")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserTags(@HeaderParam("AUTHORIZED-ID") String userId) {

	UserTagListDTO utl = new UserTagListDTO();
	utl.tags = new ArrayList<String>();
	utl.tags.add("japanese");
	utl.tags.add("fininsh");
	utl.tags.add("german");

	Response.ResponseBuilder builder = null;
	builder = Response.ok(utl);

	return builder.build();

    }

    @POST
    @JWTTokenAutorization
    @Path("/removeusertags")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRemoveTagForUser(@HeaderParam("AUTHORIZED-ID") String userId, UserTagListDTO userTagListDTO) {

	Response.ResponseBuilder builder = null;

	try {

	    validator.validateUserTag(userTagListDTO);
	    UserCreationService.removeTagForUser(userTagListDTO, userId);

	    builder = Response.ok();

	} catch (ConstraintViolationException e) {
	    builder = validator.createViolationResponse(e.getConstraintViolations());
	} catch (CSTServiceException e) {
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}

	return builder.build();

    }

}
