package com.dennisjonsson.cst.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.dennisjonsson.cst.client.RequestDTO;
import com.dennisjonsson.cst.client.RequestListDTO;
import com.dennisjonsson.cst.model.Request;

public class RequestTransformer {
	
	public static Request toRequest(RequestDTO reqDTO){
		
		Request req = new Request(reqDTO.id, reqDTO.user);
		req.content = reqDTO.content;
		req.tags = reqDTO.tags;
		
		DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		String date = reqDTO.date;
		try {
			if(date != null)
				req.date = format.parse(date);
		} catch (ParseException e) {
			// ignore null pointers and such, leave date as null
			e.printStackTrace();
		}
		
		return req;
	}
	
	public static RequestDTO toRequestDTO(Request request){
		// RequestDTO(String appKey, String id, String user, String content, ArrayList<String> tags, String date)
		RequestDTO requestDto = new RequestDTO(
					ApplicationConstants.ANDROID_APP_KEY,
					request.getId(),
					request.getUser(),
					request.content,
					request.tags,
					request.date.toString()
				);
		return requestDto;
		
	}
	
	public static RequestListDTO toRequestListDTO(ArrayList<Request> requests){
		RequestListDTO listDTO = new RequestListDTO();
		listDTO.requests = new ArrayList<RequestDTO>();
		for(Request req : requests){
			listDTO.requests.add(toRequestDTO(req));
		}
		
		return listDTO;
	}

}
