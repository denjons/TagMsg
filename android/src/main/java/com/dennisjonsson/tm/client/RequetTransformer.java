package com.dennisjonsson.tm.client;

import com.dennisjonsson.tm.model.Request;
import com.dennisjonsson.tm.model.User;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dennis on 2016-03-14.
 */
public class RequetTransformer {

    public static Request toRequest(RequestDTO requestDTO){

        // Request(String id, String user, String content, String date, ArrayList<String> tags)
        return new Request(
                requestDTO.id,
                requestDTO.user.id,
                requestDTO.content,
                requestDTO.date,
                requestDTO.tags);

    }

    public static ArrayList<Request> toRequestList(RequestListDTO requestListDTO){
        ArrayList<Request> requests = new ArrayList<>();
        for(RequestDTO requestDTO : requestListDTO.requests){
            requests.add(RequetTransformer.toRequest(requestDTO));
        }
        return requests;
    }
}
