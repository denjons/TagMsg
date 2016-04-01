package com.dennisjonsson.tm.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dennisjonsson.tagmessenger.R;
import com.dennisjonsson.tm.activity.TagListFragment.OnListFragmentInteractionListener;

import java.util.List;

/**

 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TagRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<String> mValues;
    private final AdapterInteractionListener mInteractionListener;
    private ViewHolder mSelectedTag;




    private static final int HEADER_TYPE = 0;
    private static final int ITEM_TYPE = 1;


    public TagRecyclerViewAdapter(List<String> items, AdapterInteractionListener fragment) {
        mValues = items;
        mInteractionListener = fragment;
    }

    public interface AdapterInteractionListener{
        public void onAddTag(String tag);
        public void onSelectTag(TagRecyclerViewAdapter.ViewHolder viewHolder);
        public void onDeselectTag(TagRecyclerViewAdapter.ViewHolder viewHolder);
        public void onSetHeader(HeaderViewHolder viewHolder);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == HEADER_TYPE){
            view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tag_edit_element, parent, false);
            return new HeaderViewHolder(view);
        }else{
            // ITEM_TYPE
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_tag, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if( holder instanceof ViewHolder){
            final ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.tag = mValues.get(position - 1);
            viewHolder.mIdView.setText(mValues.get(position - 1));

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deselectTag();
                }

            });

            viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectTag(viewHolder);
                    return true;
                }
            });

        }else if(holder instanceof HeaderViewHolder){
            final HeaderViewHolder viewHolder = (HeaderViewHolder)holder;
            mInteractionListener.onSetHeader(viewHolder);
            viewHolder.mAddBtnView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String text = viewHolder.mTextView.getText().toString();
                    viewHolder.progressBarContainer.setVisibility(View.VISIBLE);;
                    viewHolder.mTextView.setEnabled(false);
                    viewHolder.mAddBtnView.setEnabled(false);
                    addTag(text);
                }
            });
            viewHolder.mAddBtnView.setEnabled(false);

            viewHolder.mTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length() < 1){
                        viewHolder.mAddBtnView.setEnabled(false);
                    }else{
                        viewHolder.mAddBtnView.setEnabled(true);
                    }
                }
            });
        }

    }

    private void addTag(String tag){
        if(mInteractionListener != null){
            if(!mValues.contains(tag)){
                mInteractionListener.onAddTag(tag);
            }else{

                // TODO: NOTIFY USER
            }

        }
    }

    public void deselectTag(){
        if(mSelectedTag != null){
            mInteractionListener.onDeselectTag(mSelectedTag);
        }
    }


    public void selectTag(ViewHolder holder){

        if (null != mInteractionListener) {
            if(mSelectedTag != null){
                mInteractionListener.onDeselectTag(mSelectedTag);
            }
            mSelectedTag = holder;
            mInteractionListener.onSelectTag(mSelectedTag);


        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageButton mAddBtnView;
        public final EditText mTextView;
        public final LinearLayout progressBarContainer;
        public String tag;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mAddBtnView = (ImageButton)itemView.findViewById(R.id.tag_add_button);
            mTextView = (EditText)itemView.findViewById(R.id.tag_add_text);
            progressBarContainer = (LinearLayout)itemView.findViewById(R.id.tag_progressbar_container);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public String tag;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tag_list_item_title);
        }

        public void select(Context context){
            mView.setBackgroundColor(ContextCompat.getColor(
                    context, R.color.tag_list_selected_background));

            mIdView.setTextColor(ContextCompat.getColor(
                    context, R.color.tag_list_selected_text));
        }

        public void deselect(Context context){
            mView.setBackgroundColor(ContextCompat.getColor(
                    context,R.color.tag_list_background));
            mIdView.setTextColor(ContextCompat.getColor(
                    context, R.color.tag_text_color));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tag + "'";
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }
}
