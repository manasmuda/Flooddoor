package com.msm.floodoor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeViewHolder> {

    private List<NoticeItem> noticeItems=new ArrayList<>();

    private Context context;

    public NoticeListAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.notice_layout, parent, false);
        return new NoticeViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.bindData(noticeItems.get(position),context);
    }

    @Override
    public int getItemCount() {
        return noticeItems.size();
    }

    public void setList(List<NoticeItem> itemList){
        noticeItems=itemList;
        Log.i("abcd",String.valueOf(noticeItems.size()));
        notifyDataSetChanged();
    }

}
