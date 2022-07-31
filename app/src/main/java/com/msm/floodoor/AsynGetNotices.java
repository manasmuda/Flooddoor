package com.msm.floodoor;

import android.os.AsyncTask;

import java.util.List;

public class AsynGetNotices extends AsyncTask<Void,Void, List<NoticeItem>> {

    private NoticeListAdapter adapter;

    public AsynGetNotices(NoticeListAdapter adapter){
        this.adapter=adapter;
    }

    @Override
    protected List<NoticeItem> doInBackground(Void... voids) {
        return GlobalApplication.awsHelper.getAllNotices();
    }

    @Override
    protected void onPostExecute(List<NoticeItem> noticeItems) {
        adapter.setList(noticeItems);
        //if(noticeItems.size()==0){
          //  AsynGetNotices asynGetNotices=new AsynGetNotices(adapter);
            //asynGetNotices.execute();
        //}
    }
}
