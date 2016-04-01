package com.dennisjonsson.tm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.application.TMApplication;
import com.dennisjonsson.tm.application.TMService;
import com.dennisjonsson.tm.util.InputHandler;
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
    private InputHandler inputHandler;

    private ArrayList<String> tags;
    private LinearLayout progressBar;

    private String tagText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tags = new ArrayList<>();
        validator = new Validator();
        inputHandler = new InputHandler();
        tagEditText = (EditText)findViewById(R.id._request_create_add_tag_text);
        progressBar = (LinearLayout)findViewById(R.id.request_create_progressbar);
        requestEditText = (EditText)findViewById(R.id.request_create_content_text);
        testTagsText = (TextView)findViewById(R.id.request_create_test_tags);

        tagEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tagText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if(str.contains("\n")){
                    addTag(str);
                    Log.d(LOG_TAG,"enter");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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


        ArrayList<String> inputTags = inputHandler.hanldeTags(tag);
        try {

            for(String tagStr: inputTags){
                Log.d(LOG_TAG, tagStr);
                validator.validateTag(tagStr);
            }
        } catch (ValidatorException e) {
            // TODO: notify user with message.
            Log.d(LOG_TAG, e.getMessage());
            e.printStackTrace();
            return;
        }

        tags.addAll(inputTags);

        for(String tagStr: inputTags){
            // test
            if(testTagsText.length() > 0){

                testTagsText.append(", ");
            }
            testTagsText.append(tagStr);
            Log.d(LOG_TAG, "added tag " + tagStr);
        }

        testTagsText.setVisibility(View.VISIBLE);
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
