package com.dennisjonsson.tm.rest;


import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.RequestListDTO;
import com.dennisjonsson.tm.client.RequestUpdateDTO;
import com.dennisjonsson.tm.client.TagListDTO;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.data.RequestTransformer;
import com.dennisjonsson.tm.data.UserTransformer;
import com.dennisjonsson.tm.model.Request;
import com.dennisjonsson.tm.model.User;
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
	@Path("/addRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRequest(RequestDTO requestDTO){
		Response.ResponseBuilder builder = null;

		try{
			validator.validateUser(requestDTO.user);
			validator.validateRequest(requestDTO);
			Request request = RequestTransformer.toRequest(requestDTO);
			requestService.addRequest(request);
			builder = Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = validator.createViolationResponse(e.getConstraintViolations());
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
	}
	
	@POST
	@Path("/getRequestsForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestListDTO postGetRequestsForUser(RequestUpdateDTO requestUpdateDTO ) {
		
		try{
			validator.validateRequestsUpdate(requestUpdateDTO);
			validator.validateUser(requestUpdateDTO.user);
			RequestListDTO dto = RequestTransformer.toRequestListDTO(requestService.getRequests(requestUpdateDTO));
			return dto;
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
	@Path("/getEligibleRequestsForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestListDTO postGetEligibleRequestsForUser(RequestUpdateDTO requestsDTO){
		
		try{
			validator.validateRequestsUpdate(requestsDTO);
			validator.validateUser(requestsDTO.user);
			
			RequestListDTO dto = RequestTransformer.toRequestListDTO(
					requestService.getEligibleRequestsForUser(requestsDTO));
			return dto;
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
	@Path("/getRequestsFromTags")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestListDTO postGetRequestsFromTags(TagListDTO tagsDTO){
		
		try {
			validator.validateTags(tagsDTO);
			ArrayList<Request> requests = requestService.getRequestsFromTags(
					tagsDTO.tags, 
					tagsDTO.limit, 
					tagsDTO.offset,
					tagsDTO.endDate, 
					tagsDTO.startDate);
			
			return RequestTransformer.toRequestListDTO(requests);
		} catch(ConstraintViolationException e){
			Response.ResponseBuilder builder = 
					validator.createViolationResponse(e.getConstraintViolations());
			throw new WebApplicationException(builder.build());
			
		}catch (CSTServiceException e) {
			e.printStackTrace();
			throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
	}
	
	
}
