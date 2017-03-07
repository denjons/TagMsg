package com.dennisjonsson.tm.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.ResponseDTO;
import com.dennisjonsson.tm.client.ResponseUpdateDTO;
import com.dennisjonsson.tm.data.RequestTransformer;
import com.dennisjonsson.tm.data.ResponseTransformer;
import com.dennisjonsson.tm.entity.Request;
import com.dennisjonsson.tm.entity.RequestResult;
import com.dennisjonsson.tm.entity.Response;


@ApplicationScoped
public class ResponseService extends DataSource {
	

	
	public void addResponseToRequest(ResponseDTO responseDTO, int userId) throws CSTServiceException{
		beginTransaction();
		
		Request request = findRequestById(responseDTO.request);
		
		Response response = ResponseTransformer.toResponse(responseDTO, request.getId(), userId);
		em.persist(response);
		
		endTransaction();
	}
	
	private Request findRequestById(String UUID) throws CSTServiceException{
		Request request = em.find(Request.class, UUID);
		
		if(request == null){
			throw new CSTServiceException("request with id: '"+UUID+"' does not exist");
		}
		
		return request;
	}
	
	public ArrayList<ResponseDTO> getResponsesForRequest(ResponseUpdateDTO responseUpdateDTO) throws CSTServiceException{
		
		beginTransaction();
		Request request = findRequestById(responseUpdateDTO.request);
		endTransaction();
		
		StoredProcedureQuery storedProcedure = em.createNamedStoredProcedureQuery("getResponsesForRequest");
		
		storedProcedure.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
			.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	
		storedProcedure.setParameter(0, request.getId()).setParameter(1, responseUpdateDTO.limit)
			.setParameter(2, responseUpdateDTO.offset).setParameter(3, responseUpdateDTO.fromResponse)
			.setParameter(4, responseUpdateDTO.beforeResponse);
	
		storedProcedure.execute();
	
		ArrayList<ResponseDTO> dtos = new ArrayList<ResponseDTO>();
	
		ArrayList<Response> res = (ArrayList<Response>) storedProcedure.getResultList();
		res.stream().map((resp) -> ResponseTransformer.toResponseDTO(resp, responseUpdateDTO.request)).forEach(dtos::add);
	
		return dtos;
	}
}
