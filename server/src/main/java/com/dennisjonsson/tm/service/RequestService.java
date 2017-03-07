package com.dennisjonsson.tm.service;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.RequestUpdateDTO;
import com.dennisjonsson.tm.client.TagListDTO;
import com.dennisjonsson.tm.data.RequestTransformer;
import com.dennisjonsson.tm.entity.Request;
import com.dennisjonsson.tm.entity.RequestTagRelation;
import com.dennisjonsson.tm.util.SQLUtil;
import com.dennisjonsson.tm.entity.RequestResult;

@ApplicationScoped
public class RequestService extends DataSource {

    public void addRequest(Request request) throws CSTServiceException {

	beginTransaction();

	em.persist(request);

	request.getTagsRelations().forEach((req) -> req.setRequest(request.getId()));
	request.getTagsRelations().forEach(this::addTagsForRequest);

	endTransaction();

    }

    private void addTagsForRequest(RequestTagRelation relation) {
	StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("ADD_REQUEST_TAG")
		.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN)
		.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);

	storedProcedure.setParameter(0, relation.getRequest()).setParameter(1, relation.getTag());

	storedProcedure.execute();
    }
    // create procedure GET_USER_REQUESTS(userId integer, requestLimit int,
    // requestOffset int, fromRequest VARCHAR(40), beforeRequest VARCHAR(40))

    public ArrayList<RequestDTO> getRequests(RequestUpdateDTO requestUpdateDTO, int userId) throws CSTServiceException {
		StoredProcedureQuery storedProcedure = em.createNamedStoredProcedureQuery("getUserRequests");
	
		storedProcedure.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
			.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	
		storedProcedure.setParameter(0, userId).setParameter(1, requestUpdateDTO.limit)
			.setParameter(2, requestUpdateDTO.offset).setParameter(3, requestUpdateDTO.fromRequest)
			.setParameter(4, requestUpdateDTO.beforeRequest);
	
		storedProcedure.execute();
	
		ArrayList<RequestDTO> dtos = new ArrayList<RequestDTO>();
	
		ArrayList<RequestResult> res = (ArrayList<RequestResult>) storedProcedure.getResultList();
		res.stream().map(RequestTransformer::toRequestsDTO).forEach(dtos::add);
	
		return dtos;

    }

    public ArrayList<RequestDTO> getEligibleRequestsForUser(RequestUpdateDTO requestUpdateDTO, int userId) throws CSTServiceException {
    	StoredProcedureQuery storedProcedure = em.createNamedStoredProcedureQuery("getEligibleRequests");
    	
		storedProcedure.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
			.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	
		storedProcedure.setParameter(0, userId).setParameter(1, requestUpdateDTO.limit)
			.setParameter(2, requestUpdateDTO.offset).setParameter(3, requestUpdateDTO.fromRequest)
			.setParameter(4, requestUpdateDTO.beforeRequest);
	
		storedProcedure.execute();
	
		ArrayList<RequestDTO> dtos = new ArrayList<RequestDTO>();
	
		ArrayList<RequestResult> res = (ArrayList<RequestResult>) storedProcedure.getResultList();
		res.stream().map(RequestTransformer::toRequestsDTO).forEach(dtos::add);
	
		return dtos;
    }

    public ArrayList<RequestDTO> getRequestsFromTags(TagListDTO tagListDTO) throws CSTServiceException {
    	StoredProcedureQuery storedProcedure = em.createNamedStoredProcedureQuery("getRequestsFromTags");
    	
		storedProcedure.registerStoredProcedureParameter(0, String.class, ParameterMode.IN)
			.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
			.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
	

		storedProcedure.setParameter(0, SQLUtil.ListToString(tagListDTO.tags)).setParameter(1, tagListDTO.limit)
			.setParameter(2, tagListDTO.offset).setParameter(3, tagListDTO.startDate);
	
		storedProcedure.execute();
	
		ArrayList<RequestDTO> dtos = new ArrayList<RequestDTO>();
	
		ArrayList<RequestResult> res = (ArrayList<RequestResult>) storedProcedure.getResultList();
		res.stream().map(RequestTransformer::toRequestsDTO).forEach(dtos::add);
	
		return dtos;
    }

}
