package com.msm.floodoor;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

public class LambdaInvoker extends AsyncTask<String,Void, JSONObject> {

    private LambdaInvokeListener listener;

    public LambdaInvoker(LambdaInvokeListener listener){
        this.listener=listener;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        final int x;
        if(strings[0].equals("rain")) {
            return GlobalApplication.awsHelper.InvokeRainfallPred(strings[1], strings[2],strings[3]);
        }
        else if(strings[0].equals("flood")){
            return GlobalApplication.awsHelper.InvokeFloodPred(strings[1], strings[2],strings[3]);
        }
        else if(strings[0].equals("user")){
            return GlobalApplication.awsHelper.InvokeAddToken(strings[1],strings[2]);
        }
        else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
           listener.onResult(jsonObject);

    }

}
