package com.dennisjonsson.tm.client;

import com.dennisjonsson.tm.application.TMAppConstants;
import com.dennisjonsson.tm.model.Request;

import java.util.ArrayList;

/**
 * Created by dennis on 2016-03-14.
 */
public class RequestTransformer {

    public static Request toRequest(RequestDTO requestDTO){

        // Request(String id, String user, String content, String date, ArrayList<String> tags)
        return new Request(
                requestDTO.id,
                requestDTO.user.id,
                requestDTO.content,
                requestDTO.date,
                requestDTO.tags);

    }

    public static RequestDTO toRequestDTO(Request request){
        return new RequestDTO(
                request.id,
                new UserDTO(request.user, TMAppConstants.ANDROID_APP_KEY ),
                request.content,
                request.tags,
                request.date);
    }

    public static ArrayList<Request> toRequestList(RequestListDTO requestListDTO){
        ArrayList<Request> requests = new ArrayList<>();
        for(RequestDTO requestDTO : requestListDTO.requests){
            requests.add(RequestTransformer.toRequest(requestDTO));
        }
        return requests;
    }
}
