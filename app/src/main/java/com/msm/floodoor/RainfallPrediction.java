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

public class RainfallPrediction extends AppCompatActivity {

    private ConstraintLayout inputLayout;
    private ConstraintLayout outputLayout;

    private TextView output;
    private TextView outputResult;

    private Spinner stateDD;
    private Spinner monthDD;
    private Spinner yearDD;

    private Button submitButton;
    private CircleImageView backButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rainfall_prediction);

        stateDD=findViewById(R.id.rp_ss_spinner);
        monthDD=findViewById(R.id.rp_dp_spinner);
        yearDD=findViewById(R.id.rp_yp_spinner);
        submitButton=findViewById(R.id.rp_submit);
        backButton=findViewById(R.id.rp_back_button);
        inputLayout=findViewById(R.id.rp_input_layout);
        outputLayout=findViewById(R.id.rp_oytput_layout);
        output=findViewById(R.id.rp_output);
        outputResult=findViewById(R.id.rp_output_result);

        inputLayout.setVisibility(View.VISIBLE);
        outputLayout.setVisibility(View.GONE);

        final List<String> stateList=new ArrayList<>();
        stateList.add("ANDAMAN&NICOBARISLANDS");
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
        stateList.add("LAKSHADWEEP");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        stateList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        stateDD.setAdapter(spinnerArrayAdapter);

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
                inputLayout.setVisibility(View.GONE);
                LambdaInvoker lambdaInvoker=new LambdaInvoker(new LambdaInvokeListener() {
                    @Override
                    public void onResult(JSONObject result) {
                        outputLayout.setVisibility(View.VISIBLE);
                        try {
                            String rain=result.getString("body");
                            Log.i("abcde",rain);
                            double xyz= Double.parseDouble(rain);
                            output.setText(String.format("%.2f",xyz)+"mm");
                            if(Double.parseDouble(rain)<100){
                                outputResult.setText("LESS\nRAINFALL");
                                outputResult.setTextColor(Color.parseColor("#ff9900"));
                            }
                            else if(Double.parseDouble(rain)<500){
                                outputResult.setText("MODERATE\nRAINFALL");
                                outputResult.setTextColor(Color.parseColor("#00ff00"));
                            }
                            else {
                                outputResult.setText("HEAVY\nRAINFALL");
                                outputResult.setTextColor(Color.parseColor("#ff0000"));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                });
                lambdaInvoker.execute("rain",stateList.get(stateDD.getSelectedItemPosition()),monthList.get(monthDD.getSelectedItemPosition()),yearList.get(yearDD.getSelectedItemPosition()));
            }
        });



    }
}