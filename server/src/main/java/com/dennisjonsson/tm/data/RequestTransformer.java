package com.dennisjonsson.tm.data;

import java.util.ArrayList;

import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.RequestListDTO;
import com.dennisjonsson.tm.entity.Request;
import com.dennisjonsson.tm.entity.RequestTagRelation;
import com.dennisjonsson.tm.entity.UserRequestResult;

public class RequestTransformer {

    public static Request toRequest(RequestDTO reqDTO, int userId) {

	Request req = new Request();
	req.setUser(userId);
	req.setUu_id(reqDTO.id);
	req.setContent(reqDTO.content);

	ArrayList<RequestTagRelation> relation = new ArrayList<RequestTagRelation>();

	reqDTO.tags.stream().forEach((tag) -> relation.add(new RequestTagRelation(req.getId(), tag)));

	req.setTagsRelations(relation);

	return req;
    }

    public static RequestDTO toRequestDTO(Request request) {

	ArrayList<String> tags = new ArrayList<String>();

	request.getTagsRelations().stream().forEach((relation) -> tags.add(relation.getTag()));

	RequestDTO requestDto = new RequestDTO(request.getUu_id(), request.getContent(), tags,
		request.getDate().toString());

	return requestDto;

    }

    public static RequestListDTO toRequestListDTO(ArrayList<Request> requests) {
	RequestListDTO listDTO = new RequestListDTO();
	listDTO.requests = new ArrayList<RequestDTO>();
	for (Request req : requests) {
	    listDTO.requests.add(toRequestDTO(req));
	}

	return listDTO;
    }

    public static RequestDTO toRequestsDTO(UserRequestResult obj) {
	RequestDTO dto = new RequestDTO();
	dto.id = obj.getUu_id();
	dto.content = obj.getContent();
	dto.date = obj.getDate();
	dto.tags = new ArrayList<String>();
	for (String tag : obj.getTags().split(",")) {
	    dto.tags.add(tag);
	}

	return dto;
    }

}
