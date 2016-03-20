package com.dennisjonsson.tm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.application.TMApplication;
import com.dennisjonsson.tm.application.TMService;
import com.dennisjonsson.tm.util.Validator;
import com.dennisjonsson.tm.util.ValidatorException;

import java.util.ArrayList;

public class RequestCreateActivity extends AppCompatActivity implements
    TMService.Listener{

    public static final String LOG_TAG = "RequestCreateActivity";

    private EditText tagEditText;
    private EditText requestEditText;
    private TextView testTagsText;

    private Validator validator;

    private ArrayList<String> tags;
    private LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tags = new ArrayList<>();
        validator = new Validator();
        tagEditText = (EditText)findViewById(R.id._request_create_add_tag_text);
        progressBar = (LinearLayout)findViewById(R.id.request_create_progressbar);
        requestEditText = (EditText)findViewById(R.id.request_create_content_text);
        testTagsText = (TextView)findViewById(R.id.request_create_test_tags);

        tagEditText.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                switch(keyCode){
                    case KeyEvent.KEYCODE_ENTER:
                        if(event.getAction() != KeyEvent.ACTION_DOWN){
                            addTag(tagEditText.getText().toString());
                        }
                        break;
                    default:
                        break;

                }
                return false;
            }


        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_request_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.request_create_menu_item_send:
                addRequest(requestEditText.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTag(String tag){

        tag = tag.trim();
        try {
            validator.validateTag(tag);
        } catch (ValidatorException e) {
            // TODO: notify user with message.
            Log.d(LOG_TAG, e.getMessage());
            e.printStackTrace();
            return;
        }

        tags.add(tag);
        Log.d(LOG_TAG, "added tag " + tag);

        // test
        if(testTagsText.length() > 0){
            testTagsText.append(", ");
        }
        testTagsText.append(tag);

        tagEditText.getText().clear();

    }

    public void addRequest(String content){
        if(content.length() < 1) {
            Log.d(LOG_TAG, "length is 0, not adding request");
            return;
        }else if(tags.size() <= 0){
            Log.d(LOG_TAG, "no tags have been added, not adding request");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Log.d(LOG_TAG, "adding request: " + content);
        TMService service =  TMApplication.getTMService(this);
        service.addRequest(service.getLocalUser(), content, tags);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TMApplication.getTMService(this).addListener(this);
    }

    @Override
    protected void onPause() {
        TMApplication.getTMService(this).removelistener(this);
        super.onPause();
    }

    @Override
    public void onFinish(TMService.Response response, String message, Object result) {
        if(response == TMService.Response.SUCCESS_ADD_REQUEST){
            Log.d(LOG_TAG, "success adding request, closing activity");
            finish();
        }
        if(response == TMService.Response.FAILURE_ADD_REQUEST){
            progressBar.setVisibility(View.GONE);
            Log.d(LOG_TAG, "failure adding request");
        }
    }

}
