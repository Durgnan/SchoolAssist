package com.durgesh.schoolassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CasePresentation extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String username;
    Spinner input1,input2,input3,input4,input5,input6;
    String text1,text2,text3,text4,text5,text6;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_presentation);
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.log_out)
        {
            startActivity(new Intent(CasePresentation.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        input1=findViewById(R.id.concise);
        input2=findViewById(R.id.pertinentf);
        input3=findViewById(R.id.pertinentpn);
        input4=findViewById(R.id.differential);
        input5=findViewById(R.id.appropriate);
        input6=findViewById(R.id.response);
        addItemsOnSpinner();

        button=findViewById(R.id.submit_report2);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        text1=input1.getSelectedItem().toString().trim();
        text2=input2.getSelectedItem().toString().trim();
        text3=input3.getSelectedItem().toString().trim();
        text4=input4.getSelectedItem().toString().trim();
        text5=input5.getSelectedItem().toString().trim();
        text6=input6.getSelectedItem().toString().trim();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("CasePresentation");
        query.whereEqualTo("username", username);
        try {
            if (query.count()>0)
            {
                Toast.makeText(getApplicationContext(),"Report Already Submitted!!",Toast.LENGTH_LONG).show();
            }
            else
            {
              submit();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void submit()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Once submitted cannot be revised. Do you want to Continue ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Add The Values to database
                        ParseObject registerObject = new ParseObject("CasePresentation");
                        registerObject.put("username", username);
                        registerObject.put("Concise_Clarity",text1);
                        registerObject.put("PertinentFacts",text2);
                        registerObject.put("PertinentPositivesNegatives",text3);
                        registerObject.put("DifferentialDiagnosis",text4);
                        registerObject.put("AppropriatePlan",text5);
                        registerObject.put("ResponsetoAttending",text6);

                        registerObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                                if (e != null)
                                    Log.e("ERROR", e.getMessage());
                                else {
                                    Log.e("STATUS", "SUCCESS");
                                    Toast.makeText(getApplicationContext(),"Report Submission Successfull",Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }
                        });


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Confirmation");
        alert.show();
    }
    void addItemsOnSpinner(){
        List<String> list = new ArrayList<String>();
        list.add("0");list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input1.setAdapter(branchAdapter);
        input2.setAdapter(branchAdapter);
        input3.setAdapter(branchAdapter);
        input4.setAdapter(branchAdapter);
        input5.setAdapter(branchAdapter);
        input6.setAdapter(branchAdapter);
    }
}
