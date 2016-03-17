package com.dennisjonsson.tm.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.activity.RequestListFragment.OnListFragmentInteractionListener;

import com.dennisjonsson.tm.model.Request;

import java.util.Arrays;
import java.util.List;

/**

 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RequestRecyclerViewAdapter extends RecyclerView.Adapter<RequestRecyclerViewAdapter.ViewHolder> {

    private final List<Request> mValues;
    private final OnListFragmentInteractionListener mListener;

    private static final int ITEM_TYPE_LOAD = 0;
    private static final int ITEM_TYPE_REQUEST = 0;

    public RequestRecyclerViewAdapter(List<Request> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if(viewType == ITEM_TYPE_REQUEST){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_request, parent, false);
        }else if(viewType == ITEM_TYPE_LOAD){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_progressbar_request, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        Request request = mValues.get(position - 1);
        holder.mContent.setText(request.content);
        // TODO get tags in a nocer way
        holder.mTags.setText(Arrays.deepToString(request.tags.toArray()));
        holder.mDate.setText(request.date);
        holder.request = request;


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.request);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {

        if(getItemCount() == 1 && position == 0 ){
            return ITEM_TYPE_LOAD;
        }
        return ITEM_TYPE_REQUEST;
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
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
