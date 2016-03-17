package com.dennisjonsson.tm.rest;

import com.dennisjonsson.tm.application.TMAppConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dennis on 2016-03-10.
 */


public class RestPostRequest {



    public RestResponse Execute(String urlString, String requestString){
        HttpURLConnection urlConnection = null;
        try {

            byte [] body = requestString.getBytes();

            URL url = new URL(urlString);
            // setup connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setFixedLengthStreamingMode(body.length);
            urlConnection.setConnectTimeout(TMAppConstants.SOCKET_TIME_OUT);

            // write into stream
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(body);
            out.flush();
            out.close();

            // get response stream


            int responseLength = urlConnection.getContentLength();
            int code = urlConnection.getResponseCode();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader ir = new InputStreamReader(in);

            Scanner reader = new Scanner(ir);
            StringBuilder builder = new StringBuilder();
            while (reader.hasNext()) {
                builder.append(reader.nextLine() + "\n");
            }
            String responseText = builder.toString();

            if(code == 400){
                return new RestResponse(code, responseText,
                        RestResponse.Error.ERROR_MALFORMED_REQUEST);
            }
            if(code == 500){
                return new RestResponse(code, responseText,
                        RestResponse.Error.ERROR_SERVER_INTERNAL_ERROR);
            }

            return new RestResponse(code, responseText);


        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch(SocketTimeoutException e){
            return new RestResponse(-1, e.getMessage(), RestResponse.Error.ERROR_CONNECTION_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponse(0, e.getMessage(), RestResponse.Error.ERROR_GET_INPUT_STREAM);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

        }
    }
}
