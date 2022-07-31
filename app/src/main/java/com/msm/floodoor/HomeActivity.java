package com.msm.floodoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    private ImageView warningButton;
    private Button floodPredButton;
    private Button rainfallPredButton;

    private RecyclerView noticesList;
    private RecyclerView.LayoutManager layoutManager;

    private NoticeListAdapter noticeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        warningButton=findViewById(R.id.warning_button);
        floodPredButton=findViewById(R.id.home_flood_pred_button);
        rainfallPredButton=findViewById(R.id.home_rainfall_pred_button);

        noticesList=findViewById(R.id.notices_list);
        layoutManager=new LinearLayoutManager(this);
        noticesList.setLayoutManager(layoutManager);

        noticeListAdapter=new NoticeListAdapter(this);
        noticesList.setAdapter(noticeListAdapter);
        floodPredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,FloodPrediction.class);
                startActivity(intent);
            }
        });

        rainfallPredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,RainfallPrediction.class);
                startActivity(intent);
            }
        });

        AsynGetNotices asynGetNotices=new AsynGetNotices(noticeListAdapter);
        asynGetNotices.execute();
    }
}