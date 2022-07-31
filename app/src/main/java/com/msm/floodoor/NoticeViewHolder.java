package com.msm.floodoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class NoticeViewHolder extends RecyclerView.ViewHolder {

    private ImageView newsImage;
    private TextView titleView;
    private TextView descView;

    public NoticeViewHolder(View itemView){
        super(itemView);
        newsImage=itemView.findViewById(R.id.notice_image);
        descView=itemView.findViewById(R.id.notice_desc);
    }

    public void bindData(NoticeItem item, Context context){
        Log.i("abcde",item.getDesc());
        descView.setText(item.getDesc());
        if(item.getUri()!=null)
            Glide.with(context).load(item.getUri()).into(newsImage);
    }
}
