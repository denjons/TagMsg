package com.dennisjonsson.tm.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.dennisjonsson.tm.client.RequestDTO;
import com.dennisjonsson.tm.client.RequestListDTO;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.model.Request;
import com.dennisjonsson.tm.model.User;


public class RequestTransformer {
	
	public static Request toRequest(RequestDTO reqDTO){
		
		Request req = new Request(reqDTO.id, reqDTO.user.id);
		req.content = reqDTO.content;
		req.tags = reqDTO.tags;
		
		return req;
	}
	
	public static RequestDTO toRequestDTO(Request request){
		//RequestDTO(String id, UserDTO user, String content, ArrayList<String> tags)
		RequestDTO requestDto = new RequestDTO(
					request.getId(),
					UserTransformer.toUserDTO(new User(request.getUser())),
					request.content,
					request.tags,
					request.date
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
