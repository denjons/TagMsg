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

import com.dennisjonsson.cst.client.RequestDTO;
import com.dennisjonsson.cst.client.ResponseDTO;
import com.dennisjonsson.cst.client.ResponseListDTO;
import com.dennisjonsson.cst.data.RequestTransformer;
import com.dennisjonsson.cst.data.ResponseTransformer;
import com.dennisjonsson.cst.model.Request;
import com.dennisjonsson.cst.model.Response;
import com.dennisjonsson.cst.service.CSTServiceException;
import com.dennisjonsson.cst.service.ResponseService;

@Path("/response")
@RequestScoped
public class ResponseRESTService {
	
	@Inject
	Validator validator;
	
	@Inject
	ResponseService responseService;
	
	@POST
	@Path("/addResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response postAddResponse(ResponseDTO responseDTO){
		javax.ws.rs.core.Response.ResponseBuilder builder = null;

		try{
			
			validateRequest(responseDTO);
			Response response = ResponseTransformer.toResponse(responseDTO);
			responseService.addResponseToRequest(response);
			builder = javax.ws.rs.core.Response.ok();
		
		}catch(ConstraintViolationException e){
			builder = createViolationResponse(e.getConstraintViolations());
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
	public ResponseListDTO postGetResponsesForRequest(RequestDTO requestDTO){
		
		try{
			validateRequest(requestDTO);
			Request request = RequestTransformer.toRequest(requestDTO);
			ResponseListDTO dto = ResponseTransformer.toResponseDTOList(responseService.getResponsesForRequest(request));
			return dto;
		}
		catch(ConstraintViolationException e){
			javax.ws.rs.core.Response.ResponseBuilder builder = 
					createViolationResponse(e.getConstraintViolations());
			throw new WebApplicationException(builder.build());
			
		}
		catch (CSTServiceException e) {
			throw new WebApplicationException(javax.ws.rs.core.Response.status(
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}
	
	private void validateRequest(RequestDTO requestDTO) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<RequestDTO>> violations = validator.validate(requestDTO);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	private void validateRequest(ResponseDTO responseDTO) throws ConstraintViolationException, ValidationException {
	    // Create a bean validator and check for issues.
	    Set<ConstraintViolation<ResponseDTO>> violations = validator.validate(responseDTO);
	
	    if (!violations.isEmpty()) {
	        throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	    }
	
	}
	
	private javax.ws.rs.core.Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
      //  log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
