package com.dennisjonsson.tm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dennisjonsson.tm.model.Request;
import com.dennisjonsson.tm.model.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dennis on 2016-03-08.
 */
public class DataSource {

    private TMDatabaseHelper dbHelper;

    public DataSource(Context context) {
        this.dbHelper = new TMDatabaseHelper(context);
    }

    private User getUserFromCursor(Cursor cursor){

        if(cursor.getCount() < 1){
            return null;
        }
        cursor.moveToNext();
        String userId =
                cursor.getString(cursor.getColumnIndex(TMDatabaseHelper.COLUMN_USERS_USER));
        User user = new User(userId);

        cursor.close();

        return user;

    }

    public User getNativeUser(){
        String query = "SELECT * FROM "+TMDatabaseHelper.TABLE_USERS+";";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(query, null);
        return getUserFromCursor(cursor);
    }

    private ContentValues getValuesFromUser(User user){
        ContentValues values = new ContentValues();
        values.put(TMDatabaseHelper.COLUMN_USERS_USER, user.getId());
        return values;
    }

    public void storeUser(User user){
        dbHelper.getWritableDatabase().insert(
                TMDatabaseHelper.TABLE_USERS, null,
                getValuesFromUser(user));
    }

    private ArrayList<String> getTagsFromCursor(Cursor cursor){
        ArrayList<String> tags = new ArrayList<>();
        while(cursor.moveToNext()){
            tags.add(cursor.getString(cursor.getColumnIndex(TMDatabaseHelper.COLUMN_TAGS_TAG)));
        }
        cursor.close();

        return tags;
    }

    private ContentValues tagToContentValues(String tag){
        ContentValues values = new ContentValues();
        values.put(TMDatabaseHelper.COLUMN_TAGS_TAG, tag);
        return values;
    }

    public ArrayList<String> getTags(){
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("select * from "+
                TMDatabaseHelper.TABLE_TAGS, null);
        return getTagsFromCursor(cursor);
    }

    public void addTags(ArrayList<String> tags){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(String tag : tags){
            db.insert(TMDatabaseHelper.TABLE_TAGS, null, tagToContentValues(tag));
        }
        db.close();

    }

    public void removeUserTags(ArrayList<String> tags){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(String tag: tags){
            db.execSQL("delete from " + TMDatabaseHelper.TABLE_TAGS
                    + " where " + TMDatabaseHelper.COLUMN_TAGS_TAG
                    + " = '" + tag + "'");
        }


    }

     private ContentValues requestToContentValues(Request request){
         ContentValues values = new ContentValues();
         values.put(TMDatabaseHelper.COLUMN_REQUESTS_ID, request.id);
         values.put(TMDatabaseHelper.COLUMN_REQUESTS_USER, request.user);
         values.put(TMDatabaseHelper.COLUMN_REQUESTS_CONTENT, request.content);
         String tags = Arrays.toString(request.tags.toArray());
         values.put(TMDatabaseHelper.COLUMN_REQUESTS_TAGS, tags.substring(1, tags.length() - 1));

         return values;
    }

    public void storeRequests(ArrayList<Request> requests){
        for(Request request : requests){
            dbHelper.getWritableDatabase().insert(
                    dbHelper.TABLE_REQUESTS,
                    null,
                    requestToContentValues(request));
        }
    }

    private ArrayList<String> fromStringToTags(String tags){
        ArrayList<String> tagsList = new ArrayList<String>();
        String [] tagsArray = tags.split(",");
        for(String str : tagsArray){
            tagsList.add(str);
        }
        return tagsList;

    }

    private ArrayList<Request> getRequestsFromCursor(Cursor cursor){
        ArrayList<Request> requests = new ArrayList<>();
        while(cursor.moveToNext()){
            // Request(String id, String user, String content, String date, ArrayList<String> tags)
            Request request = new Request(
                    cursor.getString(cursor.getColumnIndex(TMDatabaseHelper.COLUMN_REQUESTS_ID)),
                    cursor.getString(cursor.getColumnIndex(TMDatabaseHelper.COLUMN_REQUESTS_USER)),
                    cursor.getString(cursor.getColumnIndex(TMDatabaseHelper.COLUMN_REQUESTS_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(TMDatabaseHelper.COLUMN_REQUESTS_DATE)),
                    fromStringToTags(
                            cursor.getString(
                                    cursor.getColumnIndex(TMDatabaseHelper.COLUMN_REQUESTS_TAGS)))
            );

        }
        cursor.close();

        return requests;
    }


    public ArrayList<Request> getRequests(){
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("select * from "+
                TMDatabaseHelper.TABLE_REQUESTS, null);
        return getRequestsFromCursor(cursor);
    }



}
