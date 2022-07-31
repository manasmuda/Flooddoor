package com.msm.floodoor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewUser extends AppCompatActivity {

    private Button continueButton;
    private Spinner statesSpinner;

    private SharedPreferences userPrefs;
    private SharedPreferences.Editor userPrefsEditor;

    private ProgressDialog progressDialog;

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NewUser.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        continueButton=findViewById(R.id.nu_continue_button);
        statesSpinner=findViewById(R.id.nu_ss_spinner);

        userPrefs=this.getSharedPreferences("UserPreferences",MODE_PRIVATE);
        userPrefsEditor=userPrefs.edit();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        final List<String> stateList=new ArrayList<>();
        stateList.add("ARUNACHAL PRADESH");
        stateList.add("ASSAM & MEGHALAYA");
        stateList.add("NAGA MANI MIZO TRIPURA");
        stateList.add("SUB HIMALAYAN WEST BENGAL & SIKKIM");
        stateList.add("GANGETIC WEST BENGAL");
        stateList.add("ORISSA");
        stateList.add("JHARKHAND");
        stateList.add("BIHAR");
        stateList.add("EAST UTTAR PRADESH");
        stateList.add("UTTARAKHAND");
        stateList.add("HARYANA DELHI & CHANDIGARH");
        stateList.add("PUNJAB");
        stateList.add("HIMACHAL PRADESH");
        stateList.add("JAMMU & KASHMIR");
        stateList.add("WEST RAJASTHAN");
        stateList.add("EAST RAJASTHAN");
        stateList.add("WEST MADHYA PRADESH");
        stateList.add("EAST MADHYA PRADESH");
        stateList.add("GUJARAT REGION");
        stateList.add("SAURASHTRA & KUTCH");
        stateList.add("KONKAN & GOA");
        stateList.add("MADHYA MAHARASHTRA");
        stateList.add("MATATHWADA");
        stateList.add("VIDARBHA");
        stateList.add("CHHATTISGARH");
        stateList.add("COASTAL ANDHRA PRADESH");
        stateList.add("TELANGANA");
        stateList.add("RAYALSEEMA");
        stateList.add("TAMIL NADU");
        stateList.add("COASTAL KARNATAKA");
        stateList.add("NORTH INTERIOR KARNATAKA");
        stateList.add("SOUTH INTERIOR KARNATAKA");
        stateList.add("KERALA");

        final List<String> riverMatcher=new ArrayList<>();
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Mahanadhi");
        riverMatcher.add("Mahanadhi");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Ganga");
        riverMatcher.add("Godavari");
        riverMatcher.add("Godavari");
        riverMatcher.add("Godavari");
        riverMatcher.add("Godavari");
        riverMatcher.add("Krishna");
        riverMatcher.add("Godavari");
        riverMatcher.add("Godavari");
        riverMatcher.add("Godavari");
        riverMatcher.add("Godavari");
        riverMatcher.add("Krishna");
        riverMatcher.add("Godavari");
        riverMatcher.add("Krishna");
        riverMatcher.add("Cauvery");
        riverMatcher.add("Cauvery");
        riverMatcher.add("Krishna");
        riverMatcher.add("Cauvery");
        riverMatcher.add("Cauvery");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        stateList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statesSpinner.setAdapter(spinnerArrayAdapter);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                userPrefsEditor.putString("newUser",stateList.get(statesSpinner.getSelectedItemPosition())).commit();
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            int i=statesSpinner.getSelectedItemPosition();
                            String river=riverMatcher.get(i);
                            String token=task.getResult().getToken();
                            LambdaInvoker invoker=new LambdaInvoker(new LambdaInvokeListener() {
                                @Override
                                public void onResult(JSONObject result) {
                                    progressDialog.dismiss();
                                    startActivityForResult(new Intent(NewUser.this, HomeActivity.class), 2);
                                }
                            });
                            invoker.execute("user",river,token);
                        }
                        else {
                            Log.i("12345",task.getException().getMessage());
                            progressDialog.dismiss();
                            startActivityForResult(new Intent(NewUser.this, HomeActivity.class), 2);
                        }
                    }
                });
            }
        });
    }
}