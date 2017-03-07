package com.dennisjonsson.tm.rest;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.RequestListDTO;
import com.dennisjonsson.tm.client.RequestUpdateDTO;
import com.dennisjonsson.tm.client.TagListDTO;
import com.dennisjonsson.tm.data.RequestTransformer;
import com.dennisjonsson.tm.entity.Request;
import com.dennisjonsson.tm.rest.provider.JWTTokenAutorization;
import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.RequestService;

@Path("/request")
@RequestScoped
public class RequestRESTService {

    @Inject
    TMValidator validator;

    @Inject
    RequestService requestService;

    @POST
    @JWTTokenAutorization
    @Path("/addRequest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRequest(@HeaderParam("AUTHORIZED-ID") String userId, RequestDTO requestDTO) {
	Response.ResponseBuilder builder = null;

	try {
	    validator.validateRequest(requestDTO);
	    Request request = RequestTransformer.toRequest(requestDTO, Integer.parseInt(userId));
	    requestService.addRequest(request);
	    builder = Response.ok();

	} catch (ConstraintViolationException e) {
	    builder = validator.createViolationResponse(e.getConstraintViolations());
	} catch (Exception e) {
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}

	return builder.build();
    }

    @GET
    @JWTTokenAutorization
    @Path("/getRequestsForUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetRequestsForUser(@HeaderParam("AUTHORIZED-ID") String userId,
	    @DefaultValue("10") @QueryParam("limit") int limit, @DefaultValue("0") @QueryParam("offset") int offset,
	    @DefaultValue("null") @QueryParam("from") String from,
	    @DefaultValue("null") @QueryParam("before") String before) {
	RequestUpdateDTO requestUpdateDTO = new RequestUpdateDTO(limit, offset, from, before);
	try {
	    validator.validateRequestsUpdate(requestUpdateDTO);
	    ArrayList<RequestDTO> dto = requestService.getRequests(requestUpdateDTO, Integer.parseInt(userId));
	    Response.ResponseBuilder builder = Response.ok(dto);
	    return builder.build();
	} catch (ConstraintViolationException e) {
	    Response.ResponseBuilder builder = validator.createViolationResponse(e.getConstraintViolations());
	    throw new WebApplicationException(builder.build());

	} catch (CSTServiceException e) {
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}
    }

    @GET
    @JWTTokenAutorization
    @Path("/getEligibleRequestsForUser")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestListDTO postGetEligibleRequestsForUser(@HeaderParam("AUTHORIZED-ID") String userId,
	    @DefaultValue("10") @QueryParam("limit") int limit, @DefaultValue("0") @QueryParam("offset") int offset,
	    @DefaultValue("null") @QueryParam("from") String from,
	    @DefaultValue("null") @QueryParam("before") String before) {
	RequestUpdateDTO requestUpdateDTO = new RequestUpdateDTO(limit, offset, from, before);
	try {
	    validator.validateRequestsUpdate(requestUpdateDTO);

	    RequestListDTO dto = RequestTransformer
		    .toRequestListDTO(requestService.getEligibleRequestsForUser(requestUpdateDTO));
	    return dto;
	} catch (ConstraintViolationException e) {
	    Response.ResponseBuilder builder = validator.createViolationResponse(e.getConstraintViolations());
	    throw new WebApplicationException(builder.build());

	} catch (CSTServiceException e) {
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}
    }

    @POST
    @Path("/getRequestsFromTags")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RequestListDTO postGetRequestsFromTags(TagListDTO tagsDTO) {

	try {
	    validator.validateTags(tagsDTO);
	    ArrayList<Request> requests = requestService.getRequestsFromTags(tagsDTO.tags, tagsDTO.limit,
		    tagsDTO.offset, tagsDTO.endDate, tagsDTO.startDate);

	    return RequestTransformer.toRequestListDTO(requests);
	} catch (ConstraintViolationException e) {
	    Response.ResponseBuilder builder = validator.createViolationResponse(e.getConstraintViolations());
	    throw new WebApplicationException(builder.build());

	} catch (CSTServiceException e) {
	    e.printStackTrace();
	    throw new WebApplicationException(
		    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
	}

    }

}
