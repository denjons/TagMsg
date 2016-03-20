package com.dennisjonsson.tm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

/**
 * Created by dennis on 2016-03-08.
 */

public class TMDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dennisjonsson.tm.sqLite";
    public static final int VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_USER = "user";
    public static final String COLUMN_USERS_DATE = "date";


    public static final String TABLE_TAGS = "tags";
    public static final String COLUMN_TAGS_TAG = "tag";
    public static final String COLUMN_TAGS_DATE = "date";

    public static final String TABLE_REQUESTS = "requests";
    public static final String COLUMN_REQUESTS_ID = "id";
    public static final String COLUMN_REQUESTS_USER = "user";
    public static final String COLUMN_REQUESTS_CONTENT = "content";
    public static final String COLUMN_REQUESTS_TAGS = "date";
    public static final String COLUMN_REQUESTS_DATE = "tags";

    private Context context;

    public TMDatabaseHelper(Context context){
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists " + TABLE_USERS + "("
                        + COLUMN_USERS_USER + " VARCHAR(40), "
                        + COLUMN_USERS_DATE + " timestamp default current_timestamp); "
        );

        db.execSQL("create table if not exists " + TABLE_TAGS + "("
                        + COLUMN_TAGS_TAG + " VARCHAR(50), "
                        + COLUMN_TAGS_DATE + " timestamp default current_timestamp); "
        );

        //db.execSQL("drop table "+TABLE_REQUESTS);

       db.execSQL("create table if not exists "+TABLE_REQUESTS+" (" +
               COLUMN_REQUESTS_ID + " VARCHAR(40)," +
               COLUMN_REQUESTS_USER + " VARCHAR(40)," +
               COLUMN_REQUESTS_CONTENT + " TEXT," +
               COLUMN_REQUESTS_DATE + "date timestamp default current_timestamp," +
               COLUMN_REQUESTS_TAGS + " TEXT," +
               "PRIMARY KEY(id))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
