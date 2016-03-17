package com.dennisjonsson.tm.application;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dennisjonsson.tm.client.NewUserDTO;
import com.dennisjonsson.tm.client.RequestListDTO;
import com.dennisjonsson.tm.client.RequestUpdateDTO;
import com.dennisjonsson.tm.client.RequetTransformer;
import com.dennisjonsson.tm.client.TagsDTO;
import com.dennisjonsson.tm.client.UserDTO;
import com.dennisjonsson.tm.client.UserTagDTO;
import com.dennisjonsson.tm.database.DataSource;
import com.dennisjonsson.tm.model.Request;
import com.dennisjonsson.tm.model.User;
import com.dennisjonsson.tm.rest.RestPostRequest;
import com.dennisjonsson.tm.rest.RestResponse;
import com.google.gson.Gson;
import java.util.ArrayList;


/**
 * Created by dennis on 2016-03-09.
 */
public class TMService {

    private static final String LOG_TAG = "TMService";

    private static TMService service;
    private DataSource database;
    public User user;

    private TMService(Context context){
        database = new DataSource(context);
        listeners = new ArrayList<Listener>();
    }

    static TMService getInstance(Context context){
        if(service == null)
            service = new TMService(context);
        return service;
    }

    public interface Listener{
        public void onFinish(Response response, String message, Object result);
    }

    public enum Response{
        SUCCESS_GET_USER,
        FAILURE_GET_USER,
        SUCCESS_GET_TAGS,
        SUCCESS_ADD_TAGS,
        FAILURE_ADD_TAGS,
        SUCCESS_REMOVE_TAGS,
        FAILURE_REMOVE_TAGS,
        SUCCESS_GET_REQUESTS,
        FAILURE_GET_REQUESTS

    }

    private final ArrayList<Listener> listeners;

    public void addListener(Listener listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void removelistener(Listener listener){
        if(listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    private void notifyListeners(Response resp, String message, Object result){
        for(Listener listener : listeners){
            listener.onFinish(resp, message, result);
        }
    }

    public void getUser(){

        new AsyncTask<Object, Void, Object>(){

            @Override
            protected Object doInBackground(Object... params) {

                // try get user from database
                User user = database.getNativeUser();

                if(user != null){
                    return user;
                }

                //if no user exists create new user on server

                // create json request
                NewUserDTO newUserDTO = new NewUserDTO(TMAppConstants.ANDROID_APP_KEY);
                Gson gson = new Gson();
                String jsonRequestString = gson.toJson(newUserDTO);
                String url = TMAppConstants.USER_SERVICE_ADD_USER;

                // post request
                RestResponse response = new RestPostRequest().Execute(url, jsonRequestString);

                Log.d(LOG_TAG,response.getResponseCode()+": "+response.getBody());
                // handle response
                if(response.getResponseCode() == 200){

                    UserDTO userDTO = (UserDTO)gson.fromJson(response.getBody(), UserDTO.class);
                    user = new User(userDTO.id);
                    database.storeUser(user);

                    return user;
                }

                return response;


            }

            @Override
            protected void onPostExecute(Object o) {

                if(o instanceof User){
                    user = (User)o;
                    notifyListeners(Response.SUCCESS_GET_USER, "success!", user);
                }else {
                    RestResponse response = (RestResponse)o;
                    notifyListeners(Response.FAILURE_GET_USER, "could not get user", response);
                }
            }

        }.execute();

    }

    public User getLocalUser(){
        if(user == null){
            Log.d(LOG_TAG, "getLocalUser: user was null!");
            user = database.getNativeUser();
        }
        return user;
    }

    public void getTags(){

        database.getTags();

        new AsyncTask<Object, Void, Object>(){

            @Override
            protected Object doInBackground(Object... params) {
                return database.getTags();
            }

            @Override
            protected void onPostExecute(Object o) {
                notifyListeners(Response.SUCCESS_GET_TAGS, "", o);
            }

        }.execute();
    }

    public void addTags(ArrayList<String> tags, User user){

        UserTagDTO tagDto = new UserTagDTO();
        tagDto.tags = tags;
        UserDTO userDTO = new UserDTO();
        userDTO.appKey = TMAppConstants.ANDROID_APP_KEY;
        userDTO.id = user.getId();
        tagDto.user = userDTO;

        new AsyncTask<UserTagDTO, Void, Object>(){

            @Override
            protected Object doInBackground(UserTagDTO... params) {
                Gson gson = new Gson();
                UserTagDTO dto = (UserTagDTO)params[0];
                String jsonString = gson.toJson(dto);

                RestResponse response = new RestPostRequest()
                        .Execute(TMAppConstants.USER_SERVICE_ADD_USER_TAGS, jsonString);

                // add new tags to database if post was successful
                // 0 is when the returned body cannot be read, which is okay in this case
                if(response.getResponseCode() == 200 || response.getResponseCode() == 0){
                    response.result = dto.tags;
                    database.addTags(dto.tags);
                }

                return response;
            }

            @Override
            protected void onPostExecute(Object o) {
                RestResponse response = (RestResponse)o;
                if(response.getResponseCode() == 200 || response.getResponseCode() == 0){
                    notifyListeners(Response.SUCCESS_ADD_TAGS, "success!", response.result);
                }
            }

        }.execute(tagDto);

    }

    public void deleteTags(ArrayList<String> tags, User user){

        UserTagDTO tagsDTO = new UserTagDTO();
        tagsDTO.tags = tags;
        tagsDTO.user = new UserDTO(user.getId(), TMAppConstants.ANDROID_APP_KEY);

        new AsyncTask<Object, Void, Object>(){

            @Override
            protected Object doInBackground(Object... params) {
                UserTagDTO tagsDTO = (UserTagDTO)params[0];
                Gson gson = new Gson();
                String jsonString = gson.toJson(tagsDTO);
                String url = TMAppConstants.USER_SERVICE_REMOVE_USER_TAGS;

                RestResponse response = new RestPostRequest().Execute(url, jsonString);

                int code = response.getResponseCode();
                if( code == 200 || code == 0){
                    database.removeUserTags(tagsDTO.tags);
                }

                return response;
            }

            @Override
            protected void onPostExecute(Object o) {
                RestResponse response = (RestResponse)o;
                int code = response.getResponseCode();
                if( code == 200 || code == 0){
                    notifyListeners(Response.SUCCESS_REMOVE_TAGS, "success", o);
                }else{
                    notifyListeners(Response.FAILURE_REMOVE_TAGS, "failure", o);
                }

            }
        }.execute(tagsDTO);

    }

    public void getRequestsFromTags(ArrayList<String> tags, int limit, int offset){
        TagsDTO tagDTO = new TagsDTO(limit, offset, tags);
        new AsyncTask<TagsDTO, Void, RestResponse>(){

            @Override
            protected RestResponse doInBackground(TagsDTO... params) {

                TagsDTO dto = params[0];
                Gson gson = new Gson();
                String jsonString = gson.toJson(dto);

                RestResponse response = new RestPostRequest().Execute(
                        TMAppConstants.REQUEST_SERVICE_GET_REQUESTS_FROM_TAGS,
                        jsonString);

                int code = response.getResponseCode();

                if(code == 200){
                    RequestListDTO requestList = (RequestListDTO)gson.fromJson(
                            response.getBody(),
                            RequestListDTO.class);

                    RequestManager requestManager = TMApplication.getRequestManager();
                    ArrayList<Request> requests =  RequetTransformer.toRequestList(requestList);
                    if(requestList.requests.size() < TMAppConstants.REQUEST_UPADTE_REQUESTS_LIMIT){
                        requestManager.addRequests(requests);
                    }else{
                        requestManager.setRequests(requests);
                    }
                }

                return response;

            }

            @Override
            protected void onPostExecute(RestResponse response) {

                if(response.getResponseCode() == 200){
                    notifyListeners(Response.SUCCESS_GET_REQUESTS, "success", null);
                }else if(response.getResponseCode() == -1){
                    notifyListeners(Response.FAILURE_GET_REQUESTS,
                            "Can't reach the server at the moment", response);
                }else{
                    notifyListeners(Response.FAILURE_GET_REQUESTS,
                            "A problem accured while fetching requests", response);
                }
            }
        }.execute();
    }

    public void getEligibleRequests(User user, int limit, int offset){

        RequestUpdateDTO updateDTO = new RequestUpdateDTO(
                new UserDTO(user.getId(), TMAppConstants.ANDROID_APP_KEY),
                limit,
                offset);

        new AsyncTask<RequestUpdateDTO, Void, RestResponse>(){

            @Override
            protected RestResponse doInBackground(RequestUpdateDTO... params) {

                RequestUpdateDTO dto = params[0];
                Gson gson = new Gson();
                String jsonString = gson.toJson(dto);

                RestResponse response = new RestPostRequest().Execute(
                        TMAppConstants.REQUEST_SERVICE_GET_ELIGIBLE_REQUESTS,
                        jsonString);

                int code = response.getResponseCode();

                if(code == 200){

                    RequestListDTO requestList = (RequestListDTO)gson.fromJson(
                            response.getBody(),
                            RequestListDTO.class);

                    RequestManager requestManager = TMApplication.getRequestManager();
                    ArrayList<Request> requests =  RequetTransformer.toRequestList(requestList);
                    response.result = requests;
                    requestManager.setRequests(requests);

                }

                return response;

            }

            @Override
            protected void onPostExecute(RestResponse response) {

                Log.d(LOG_TAG, response.getResponseCode()
                        +": "+response.getBody());

                if(response.getResponseCode() == 200 ){
                    notifyListeners(Response.SUCCESS_GET_REQUESTS, "success", response.result);
                }else{
                    notifyListeners(Response.FAILURE_GET_REQUESTS,
                            "A problem accured while fetching requests", response);
                }
            }
        }.execute(updateDTO);
    }



     /*
    private void createUser(){
        try {
            new AsyncTask<URL, Void, Object>(){

                @Override
                protected Object doInBackground(URL... params) {
                    URL url = params[0];
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    NewUserDTO userDTO = new NewUserDTO(TMAppConstants.ANDROID_APP_KEY);
                    UserDTO dto = restTemplate.postForObject(url.toString(), userDTO, UserDTO.class);
                    return null;
                }

            }.execute(new URL(TMAppConstants.TM_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    */




}
