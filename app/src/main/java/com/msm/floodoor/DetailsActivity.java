package com.msm.floodoor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailsActivity extends AppCompatActivity {

    private Button continueButton;

    private SharedPreferences userPrefs;
    private SharedPreferences.Editor userPrefsEditor;

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DetailsActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        continueButton=findViewById(R.id.ad_continue_button);

        userPrefs=this.getSharedPreferences("UserPreferences",MODE_PRIVATE);
        userPrefsEditor=userPrefs.edit();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userPrefs.contains("newUser")) {
                    startActivityForResult(new Intent(DetailsActivity.this, NewUser.class), 1);
                }
                else {
                    startActivityForResult(new Intent(DetailsActivity.this, HomeActivity.class), 1);
                }
            }
        });
    }
}