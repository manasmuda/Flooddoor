package com.msm.floodoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FloodPrediction extends AppCompatActivity {

    private ConstraintLayout inputLayout;
    private ConstraintLayout outputLayout;

    private TextView output;
    private TextView outputResult;

    private Spinner riverDD;
    private Spinner monthDD;
    private Spinner yearDD;

    private Button submitButton;
    private CircleImageView backButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood_prediction);
        riverDD=findViewById(R.id.fp_sr_spinner);
        monthDD=findViewById(R.id.fp_dp_spinner);
        yearDD=findViewById(R.id.fp_yp_spinner);
        submitButton=findViewById(R.id.fp_submit);
        backButton=findViewById(R.id.fp_back_button);
        inputLayout=findViewById(R.id.fp_input_layout);
        outputLayout=findViewById(R.id.fp_oytput_layout);
        output=findViewById(R.id.fp_output);

        inputLayout.setVisibility(View.VISIBLE);
        outputLayout.setVisibility(View.GONE);

        final List<String> riverList=new ArrayList<>();
        riverList.add("Ganga");
        riverList.add("Godavari");
        riverList.add("Cauvery");
        riverList.add("Krishna");
        riverList.add("Mahanadhi");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, riverList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        riverDD.setAdapter(spinnerArrayAdapter);

        final List<String> monthList=new ArrayList<>();
        monthList.add("January");
        monthList.add("Febuary");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        monthDD.setAdapter(spinnerArrayAdapter1);

        final List<String> yearList=new ArrayList<>();
        yearList.add("2021");
        yearList.add("2022");
        yearList.add("2023");
        yearList.add("2024");
        yearList.add("2025");

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, yearList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        yearDD.setAdapter(spinnerArrayAdapter2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                LambdaInvoker lambdaInvoker=new LambdaInvoker(new LambdaInvokeListener() {
                    @Override
                    public void onResult(JSONObject result) {
                        inputLayout.setVisibility(View.GONE);
                        outputLayout.setVisibility(View.VISIBLE);
                        try {
                            String flood=result.getString("body");
                            Log.i("abcde flood",flood);
                            if(Integer.parseInt(flood)==0){
                                output.setText("NO\nFLOODS\nPREDICTED");
                                output.setTextColor(Color.parseColor("#ff9900"));
                            }
                            else if(Double.parseDouble(flood)==1){
                                output.setText("HEAVY\nFLOODS\nPREDICTED");
                                output.setTextColor(Color.parseColor("#ff0000"));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                });
                lambdaInvoker.execute("flood",riverList.get(riverDD.getSelectedItemPosition()),monthList.get(monthDD.getSelectedItemPosition()),yearList.get(yearDD.getSelectedItemPosition()));
            }
        });


    }
}