package com.dennisjonsson.tm.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dennisjonsson.tagmessenger.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RequestViewActivityFragment extends Fragment {

    public RequestViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_view, container, false);
    }
}
