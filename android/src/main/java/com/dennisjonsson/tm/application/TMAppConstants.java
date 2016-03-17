package com.dennisjonsson.tm.application;

/**
 * Created by dennis on 2016-03-08.
 */
public class TMAppConstants {

    // REST
    public static final String ANDROID_APP_KEY = "TM_ANDROID_f691e54a-7ada-45a4-90e0-350ad72a51c3";
    public static final String WILDFLY_APP_KEY = "TM_WILDFLY_71aa3cd7-3205-4c30-804a-2dd89a9b5f68";
    public static final String TM_URL = "http://tm-dennisjonsson.rhcloud.com/rest";
    public static final String USER_SERVICE = "/user";
    public static final String REQUEST_SERVICE = "/request";
    public static final String USER_SERVICE_ADD_USER = TM_URL + USER_SERVICE + "/adduser";
    public static final String USER_SERVICE_ADD_USER_TAGS = TM_URL + USER_SERVICE + "/addusertags";
    public static final String USER_SERVICE_REMOVE_USER_TAGS = TM_URL + USER_SERVICE + "/removeusertags";
    public static final String REQUEST_SERVICE_GET_REQUESTS_FROM_TAGS = TM_URL + REQUEST_SERVICE + "/getRequestsFromTags";
    public static final String REQUEST_SERVICE_GET_ELIGIBLE_REQUESTS = TM_URL + REQUEST_SERVICE + "/getEligibleRequestsForUser";

    // URL connection
    public static final int SOCKET_TIME_OUT = 5000;


    // splash
    public static final int SPLASH_TIME = 2000;

    // requests
    public static final int REQUEST_UPADTE_REQUESTS_LIMIT = 15;



}
