package com.dennisjonsson.tm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.application.TMApplication;
import com.dennisjonsson.tm.application.TMService;
import com.dennisjonsson.tm.model.User;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TagListFragment extends Fragment implements
        TMService.Listener,
        TagListViewAdapter.AdapterInteractionListener,
        PopupMenu.OnMenuItemClickListener {

    public static final String LOG_TAG = "TagListFragment";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private TagListViewAdapter.HeaderViewHolder headerViewHolder;
    private TagListViewAdapter.ViewHolder selectedHolder;


    private ArrayList<String> tags;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TagListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TagListFragment newInstance(int columnCount) {
        TagListFragment fragment = new TagListFragment();
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

        View containerView = inflater.inflate(R.layout.fragment_tag_list, container, false);
        View view = containerView.findViewById(R.id.tag_list);

        // Set the adapter
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // create tags list
            tags = new ArrayList<String>();
            tags.add("japanese");
            tags.add("swedish");
            recyclerView.setAdapter(new TagListViewAdapter(tags, this));
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
        TMApplication.getTMService(getActivity()).getTags();
    }

    @Override
    public void onFinish(TMService.Response response, String message, Object result) {

        if(response == TMService.Response.SUCCESS_GET_TAGS){
            Log.d(LOG_TAG, "success getting tags.");
            ArrayList<String> newTags = (ArrayList<String>)result;
            tags.clear();
            tags.addAll(newTags);
            recyclerView.getAdapter().notifyDataSetChanged();
        }else if(response == TMService.Response.SUCCESS_ADD_TAGS){
            Log.d(LOG_TAG, "success adding tag. Getting all tags.");
            TMApplication.getTMService(getActivity()).getTags();
            if(headerViewHolder != null){
                headerViewHolder.progressBarContainer.setVisibility(View.GONE);
                headerViewHolder.mTextView.setText("");
                headerViewHolder.mTextView.setEnabled(true);
                headerViewHolder.mAddBtnView.setEnabled(true);
            }
        }else if(response == TMService.Response.FAILURE_ADD_TAGS){
            headerViewHolder.progressBarContainer.setVisibility(View.GONE);
        }
        else if(response == TMService.Response.SUCCESS_GET_USER){
            Log.d(LOG_TAG, "got user");
        }else if (response == TMService.Response.SUCCESS_REMOVE_TAGS){
            Log.d(LOG_TAG, "deleted_tags");
            if(selectedHolder != null){
                selectedHolder.deselect(getActivity());
            }
            TMApplication.getTMService(getActivity()).getTags();
        }

    }

    /*
    *   methods called by the adapterClass on listitem interactions
    * */
    @Override
    public void onAddTag(String tag){

        mListener.onListFragmentInteraction(
                tag,
                TagListFragment.InteractionEvent.ADD_TAG);

        TMService service = TMApplication.getTMService(getActivity());
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag);
        service.addTags(tags, service.getLocalUser());
    }

    @Override
    public void onSelectTag(TagListViewAdapter.ViewHolder viewHolder){
        selectedHolder = viewHolder;
        selectedHolder.select(getActivity());
        PopupMenu popupMenu = new PopupMenu(getActivity(), viewHolder.mView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_popup_tag_list);
        popupMenu.show();

        /*
        mListener.onListFragmentInteraction(
                selectedViewHolders,
                InteractionEvent.TAG_SELECTED);
                */

    }

    @Override
    public void onDeselectTag(TagListViewAdapter.ViewHolder viewHolder) {


        viewHolder.deselect(getActivity());
        /*
        mListener.onListFragmentInteraction(
                selectedViewHolders,
                InteractionEvent.TAG_DESELECTED);
                */

    }

    @Override
    public void onSetHeader(TagListViewAdapter.HeaderViewHolder viewHolder) {
        headerViewHolder = viewHolder;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if(item.getItemId() == R.id.menu_popun_taglist_delete){
            ArrayList<String> tags = new ArrayList<>();
            tags.add(selectedHolder.tag);
            TMService service = TMApplication.getTMService(getActivity());
            service.deleteTags(tags, service.getLocalUser());
        }

        return false;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Object item, InteractionEvent eventType);
    }

    public enum InteractionEvent{
        ADD_TAG,
        TAG_SELECTED,
        TAG_DESELECTED
    }
}
