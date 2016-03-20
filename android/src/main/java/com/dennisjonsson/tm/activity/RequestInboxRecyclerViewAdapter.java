package com.dennisjonsson.tm.activity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.activity.RequestInboxListFragment.OnListFragmentInteractionListener;

import com.dennisjonsson.tm.model.Request;

import java.util.Arrays;
import java.util.List;

/**

 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RequestInboxRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String LOG_TAG = "RequestInboxAdapter";

    private final List<Request> mValues;
    private final OnListFragmentInteractionListener mListener;

    private static final int ITEM_TYPE_LOAD = 0;
    private static final int ITEM_TYPE_REQUEST = 1;

    public RequestInboxRecyclerViewAdapter(List<Request> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if(viewType == ITEM_TYPE_LOAD){
            Log.d(LOG_TAG, "creating emptyViewHolder");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_progressbar_request, parent, false);
            return new EmptyViewHolder(view);

        }else if(viewType == ITEM_TYPE_REQUEST){
            Log.d(LOG_TAG, "creating requestViewHolder");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_request, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof ViewHolder){
            final ViewHolder viewHolder = (ViewHolder)holder;
            Request request = mValues.get(position);
           // Log.d(LOG_TAG, "Binding log tag with content: "+ request.content);
            viewHolder.mContent.setText(request.content);
            // TODO get tags in a nocer way
            viewHolder.mTags.setText(Arrays.deepToString(request.tags.toArray()));
            viewHolder.mDate.setText(request.date);
            viewHolder.request = request;


            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(viewHolder.request);
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(this.mValues.size() == 0 && position == 0 ){
            return ITEM_TYPE_LOAD;
        }
        return ITEM_TYPE_REQUEST;
    }

    /*
    *   return list size if list contains element or
    * */
    @Override
    public int getItemCount() {
        //Log.d(LOG_TAG, "current size of values is "+mValues.size());
        if(mValues.size() == 0){
            return 1;
        }
        return mValues.size();

    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final View mProgressBar;
        public final View mEmptList;
        public EmptyViewHolder(View view) {
            super(view);
            mView = view;
            mProgressBar = (LinearLayout)view.findViewById(R.id.request_empty_list_container);
            mEmptList = (LinearLayout)view.findViewById(R.id.request_list_progressbar_container);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContent;
        public final TextView mTags;
        public final TextView mDate;
        public Request request;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContent = (TextView) view.findViewById(R.id.request_lit_item_content);
            mTags = (TextView) view.findViewById(R.id.request_list_item_tags);
            mDate = (TextView) view.findViewById(R.id.request_list_item_date);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContent.getText() + "'";
        }
    }
}
