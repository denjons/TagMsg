package com.dennisjonsson.tm.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dennisjonsson.tm.client.ResponseDTO;
import com.dennisjonsson.tm.client.ResponseListDTO;
import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.model.Response;

public class ResponseTransformer {
	
	public static Response toResponse(ResponseDTO responseDTO){
		
		DateFormat format = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		String dateStr = responseDTO.date;
		Date date = null;
		
		try {
			if(dateStr != null)
				date = format.parse(dateStr);
		} catch (ParseException e) {
			// ignore
			e.printStackTrace();
		}
		
		Response response = new Response(responseDTO.id, responseDTO.user.id, responseDTO.request,date );
		response.content = responseDTO.content;
		
		return response;
	}
	
	public static ResponseDTO toResponseDTO(Response response){
		// ResponseDTO(String id, String user, String request, String content, String date)
		
		ResponseDTO responseDTO = new ResponseDTO(
				response.getId(),
			    UserTransformer.toUserDTO(new User(response.getUser())),
				response.getRequest(),
				response.content,
				response.date.toString());
		
		return responseDTO;
		
	}
	
	public static ResponseListDTO toResponseDTOList(ArrayList<Response> responses){
		
		ArrayList<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		
		for(Response response : responses){
			responseDTOs.add(toResponseDTO(response));
		}
		
		return new ResponseListDTO(responseDTOs);
	}
}
