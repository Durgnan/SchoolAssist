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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class OPDClinicalActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String username;
    EditText input1,input2,input3,input4,input5;
    String text1,text2,text3,text4,text5;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdclinical);
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
            startActivity(new Intent(OPDClinicalActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        input1=findViewById(R.id.asps);
        input2=findViewById(R.id.signs);
        input3=findViewById(R.id.knowledge);
        input4=findViewById(R.id.discussion);
        input5=findViewById(R.id.interaction);

        button=findViewById(R.id.submit_report4);
        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        text1=input1.getText().toString().trim();
        text2=input2.getText().toString().trim();
        text3=input3.getText().toString().trim();
        text4=input4.getText().toString().trim();
        text5=input5.getText().toString().trim();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("OPDClinical");
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
                        ParseObject registerObject = new ParseObject("OPDClinical");
                        registerObject.put("username", username);
                        registerObject.put("Anterior_Segment_Posterior_Segment_Examination",text1);
                        registerObject.put("Interpretation_of_Signs",text2);
                        registerObject.put("Knowledge_Base",text3);
                        registerObject.put("Discussion_of_Case_Application_of_Knowledge",text4);
                        registerObject.put("Interaction_with_patient",text5);

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
}