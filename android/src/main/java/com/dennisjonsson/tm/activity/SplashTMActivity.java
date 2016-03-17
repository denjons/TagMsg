package com.dennisjonsson.tm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.application.TMAppConstants;
import com.dennisjonsson.tm.application.TMApplication;
import com.dennisjonsson.tm.application.TMService;

public class SplashTMActivity extends AppCompatActivity implements TMService.Listener {

    private TextView progresstext;
    private LinearLayout progressContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_tm);
        progresstext = (TextView)findViewById(R.id.splash_progress_text);
        progressContainer = (LinearLayout)findViewById(R.id.splash_progress_container);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */
        progresstext.setText(getResources().getText(R.string.fetching_user));
        TMService service = TMApplication.getTMService(SplashTMActivity.this);
        service.addListener(SplashTMActivity.this);
        service.getUser();

    }

    @Override
    public void onFinish(TMService.Response response, String message, Object result) {
        if(response == TMService.Response.SUCCESS_GET_USER){
            progresstext.setText(getResources().getText(R.string.user_fetched));
            progressContainer.setVisibility(View.GONE);
            new android.os.Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     Intent i = new Intent(SplashTMActivity.this, MainTMActivity.class);
                     startActivity(i);
                     finish();
                 }
             }, TMAppConstants.SPLASH_TIME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TMApplication.getTMService(SplashTMActivity.this).removelistener(SplashTMActivity.this);
    }
}
