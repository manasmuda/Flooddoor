package com.msm.floodoor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class WarningActivity extends AppCompatActivity {

    private SharedPreferences userPrefs;
    private SharedPreferences.Editor userPrefsEditor;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        userPrefs=this.getSharedPreferences("UserPreferences",MODE_PRIVATE);
        userPrefsEditor=userPrefs.edit();

        textView=findViewById(R.id.aw_message);

        String river=getIntent().getStringExtra("river");
        String month=getIntent().getStringExtra("month");
        String region=userPrefs.getString("newUser","Telangana");

        textView.setText("Heavy floods are predicted in "+region +" in "+month+" along "+river +" river");

    }
}