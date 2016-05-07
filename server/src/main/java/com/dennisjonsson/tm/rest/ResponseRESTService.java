package com.dennisjonsson.tm.rest;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.dennisjonsson.tm.client.ResponseDTO;
import com.dennisjonsson.tm.client.ResponseListDTO;
import com.dennisjonsson.tm.client.ResponseUpdateDTO;
import com.dennisjonsson.tm.data.ResponseTransformer;
import com.dennisjonsson.tm.model.Response;
import com.dennisjonsson.tm.service.CSTServiceException;
import com.dennisjonsson.tm.service.ResponseService;

@Path("/response")
@RequestScoped
public class ResponseRESTService {
	
	@Inject
	CSTValidator validator;
	
	@Inject
	ResponseService responseService;
	
	@POST
	@Path("/addResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response postAddResponse(ResponseDTO responseDTO){
		javax.ws.rs.core.Response.ResponseBuilder builder = null;

		try{
			
			validator.validateResponse(responseDTO);
			validator.validateUser(responseDTO.user);
			Response response = ResponseTransformer.toResponse(responseDTO);
			responseService.addResponseToRequest(response);
			builder = javax.ws.rs.core.Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = validator.createViolationResponse(e.getConstraintViolations());
		} catch (CSTServiceException e) {
			throw new WebApplicationException(javax.ws.rs.core.Response.status(
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
		
		return builder.build();
	}
	
	@POST
	@Path("/getResponsesForRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseListDTO postGetResponsesForRequest(ResponseUpdateDTO responseUpdateDTO){
		
		try{
			validator.validateResponseUpdateDTO(responseUpdateDTO);
			validator.validateUser(responseUpdateDTO.user);
			ResponseListDTO dto = ResponseTransformer.toResponseDTOList(responseService.getResponsesForRequest(responseUpdateDTO));
			return dto;
		}
		catch(ConstraintViolationException e){
			javax.ws.rs.core.Response.ResponseBuilder builder = 
					validator.createViolationResponse(e.getConstraintViolations());
			throw new WebApplicationException(builder.build());
			
		}
		catch (CSTServiceException e) {
			throw new WebApplicationException(javax.ws.rs.core.Response.status(
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}
	


}
