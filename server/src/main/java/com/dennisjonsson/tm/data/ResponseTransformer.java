package com.dennisjonsson.tm.data;



import com.dennisjonsson.tm.client.ResponseDTO;
import com.dennisjonsson.tm.entity.Response;

public class ResponseTransformer {

    public static Response toResponse(ResponseDTO responseDTO, int request, int user) {
    	
		Response response = new Response(responseDTO.id, request, responseDTO.content);
	
		return response;
    }

    public static ResponseDTO toResponseDTO(Response response, String request) {

		ResponseDTO responseDTO = new ResponseDTO(response.getUu_id(), request, response.getContent(),
			response.getDate().toString());
	
		return responseDTO;
	
	}
	
}
