package com.dennisjonsson.tm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.application.TMApplication;
import com.dennisjonsson.tm.application.TMService;
import com.dennisjonsson.tm.model.Request;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 *
 * interface.
 */
public class RequestOutboxListFragment extends Fragment implements TMService.Listener {

    private static final String LOG_TAG = "RequestOutbox";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnRequestFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty construcstor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequestOutboxListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RequestOutboxListFragment newInstance(int columnCount) {
        RequestOutboxListFragment fragment = new RequestOutboxListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_outbox_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView)view;
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new RequestRecyclerViewAdapter(TMApplication.getRequestManager().outbox, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRequestFragmentInteractionListener) {
            mListener = (OnRequestFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        TMApplication.getTMService(getActivity()).removelistener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        TMApplication.getTMService(getActivity()).addListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        TMService service = TMApplication.getTMService(getActivity());
        service.addListener(this);
        service.getOutbox(service.getLocalUser(),0);

    }

    @Override
    public void onFinish(TMService.Response response, String message, Object result) {

        if(response == TMService.Response.SUCCESS_GET_OUTBOX){
            ArrayList<Request> list = (ArrayList<Request>)result;
            Log.d(LOG_TAG, "got outbox from database "+list.size());
            recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
