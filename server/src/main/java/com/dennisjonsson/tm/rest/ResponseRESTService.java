package com.dennisjonsson.tm.rest;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.dennisjonsson.tm.client.ResponseDTO;
import com.dennisjonsson.tm.client.ResponseUpdateDTO;
import com.dennisjonsson.tm.rest.provider.JWTTokenAutorization;
import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.ResponseService;

@Path("/response")
@RequestScoped
public class ResponseRESTService {

    @Inject
    TMValidator validator;

    @Inject
    ResponseService responseService;

    @POST
    @Path("/addResponse")
    @JWTTokenAutorization
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAddResponse(@HeaderParam("AUTHORIZED-ID") String userId, ResponseDTO responseDTO) {
    	ResponseBuilder builder = null;

		try {
		    validator.validateResponse(responseDTO);
		    responseService.addResponseToRequest(responseDTO, Integer.parseInt(userId));
		    builder = Response.ok();
	
		} catch (ConstraintViolationException e) {
		    builder = validator.createViolationResponse(e.getConstraintViolations());
		} catch (CSTServiceException e) {
		    throw new WebApplicationException(javax.ws.rs.core.Response
			    .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	
		return builder.build();
    }

    @POST
    @Path("/getResponsesForRequest")
    @JWTTokenAutorization
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetResponsesForRequest(@HeaderParam("AUTHORIZED-ID") String userId,
    		@DefaultValue("10") @QueryParam("limit") int limit, @DefaultValue("0") @QueryParam("offset") int offset,
    	    @DefaultValue("null") @QueryParam("from") String from,
    	    @DefaultValue("null") @QueryParam("before") String before,
    	    @DefaultValue("null") @QueryParam("request") String request) {
    		ResponseUpdateDTO responseUpdateDTO = new ResponseUpdateDTO(request, limit, offset, from, before);
		try {
		    validator.validateResponseUpdateDTO(responseUpdateDTO);
	
		    ArrayList<ResponseDTO> dto = responseService.getResponsesForRequest(responseUpdateDTO);
		    ResponseBuilder builder = Response.ok(dto);
		    return builder.build();
		    
		} catch (ConstraintViolationException e) {
		    javax.ws.rs.core.Response.ResponseBuilder builder = validator
			    .createViolationResponse(e.getConstraintViolations());
		    throw new WebApplicationException(builder.build());
	
		} catch (CSTServiceException e) {
		    throw new WebApplicationException(javax.ws.rs.core.Response
			    .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
    }

}
