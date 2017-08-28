package com.studio.pogafresh.gitlagjavadevs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PoG~Afresh on 8/28/2017.
 */
public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ListUserAdapterViewHolder>  {

    Context myContext;
    private String[] myUserData;


    private final ListUserAdapterOnClickHandler mClickHandler;


    public interface ListUserAdapterOnClickHandler {
        void onClick(String wearDay);
    }


    public ListUserAdapter(@NonNull Context context, ListUserAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        myContext = context;
    }


    public class ListUserAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myUserName;
        ImageView myUserImage;



        public ListUserAdapterViewHolder(View itemView) {
            super(itemView);
            myUserName = (TextView) itemView.findViewById(R.id.user_name);
            myUserImage = (ImageView) itemView.findViewById(R.id.user_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String clickData = myUserData[adapterPosition];
            mClickHandler.onClick(clickData);
        }
    }

    @Override
    public ListUserAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        myContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.user_list_item;
        LayoutInflater inflater = LayoutInflater.from(myContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new ListUserAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListUserAdapterViewHolder holder, int position) {
        String userData = myUserData[position];
        String[] parts = userData.split("-");

        String username = parts[0];
        String userurl = parts[1];
        String userimage = parts[2];

        holder.myUserName.setText(username);
        //Glide.with(myContext).load(userimage)
          //     .placeholder(R.drawable.imageholder)
            //   .error(R.drawable.imageholder)
              //.into(holder.myUserImage);
    }
    @Override
    public int getItemCount() {
        if (null == myUserData) return 0;
        return myUserData.length;


    }

    public void setUserData(String[] userData) {
        myUserData = userData;
        notifyDataSetChanged();
    }


}
