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
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class LTS extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String username;
    Spinner input1,input2,input3,input4,input5,input6,input7,input8,input9,input10,input11,input12,input13,input14,input15,input16,input17;
    String text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14,text15,text16,text17;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lts);
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
            startActivity(new Intent(LTS.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        input1=findViewById(R.id.laa);
        input2=findViewById(R.id.pad);
        input3=findViewById(R.id.incision);
        input4=findViewById(R.id.lts1);
        input5=findViewById(R.id.lts2);
        input6=findViewById(R.id.dissection);
        input7=findViewById(R.id.suturing1);
        input8=findViewById(R.id.suturing2);
        input9=findViewById(R.id.suturing3);
        input10=findViewById(R.id.mh);
        input11=findViewById(R.id.atv);
        input12=findViewById(R.id.rot);
        input13=findViewById(R.id.koi);
        input14=findViewById(R.id.snmt);
        input15=findViewById(R.id.sem);
        input16=findViewById(R.id.ofp);
        input17=findViewById(R.id.comms);
        addItemsOnSpinner();


        button=findViewById(R.id.lts_submit);
        button.setOnClickListener(this);


    }
    void  addItemsOnSpinner()
    {
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
        input7.setAdapter(branchAdapter);
        input8.setAdapter(branchAdapter);
        input9.setAdapter(branchAdapter);
        input10.setAdapter(branchAdapter);
        input11.setAdapter(branchAdapter);
        input12.setAdapter(branchAdapter);
        input13.setAdapter(branchAdapter);
        input14.setAdapter(branchAdapter);
        input15.setAdapter(branchAdapter);
        input16.setAdapter(branchAdapter);
        input17.setAdapter(branchAdapter);


    }

    @Override
    public void onClick(View v) {
        text1=input1.getSelectedItem().toString().trim();
        text2=input2.getSelectedItem().toString().trim();
        text3=input3.getSelectedItem().toString().trim();
        text4=input4.getSelectedItem().toString().trim();
        text5=input5.getSelectedItem().toString().trim();
        text6=input6.getSelectedItem().toString().trim();
        text7=input7.getSelectedItem().toString().trim();
        text8=input8.getSelectedItem().toString().trim();
        text9=input9.getSelectedItem().toString().trim();
        text10=input10.getSelectedItem().toString().trim();
        text11=input11.getSelectedItem().toString().trim();
        text12=input12.getSelectedItem().toString().trim();
        text13=input13.getSelectedItem().toString().trim();
        text14=input14.getSelectedItem().toString().trim();
        text15=input15.getSelectedItem().toString().trim();
        text16=input16.getSelectedItem().toString().trim();
        text17=input17.getSelectedItem().toString().trim();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("LTS");
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
                        ParseObject registerObject = new ParseObject("LTS");
                        registerObject.put("username", username);
                        registerObject.put("Topic1",text1);
                        registerObject.put("Topic2",text2);
                        registerObject.put("Topic3",text3);
                        registerObject.put("Topic4",text4);
                        registerObject.put("Topic5",text5);
                        registerObject.put("Topic6",text6);
                        registerObject.put("Topic7",text7);
                        registerObject.put("Topic8",text8);
                        registerObject.put("Topic9",text9);
                        registerObject.put("Topic10",text10);
                        registerObject.put("Topic11",text11);
                        registerObject.put("Topic12",text12);
                        registerObject.put("Topic13",text13);
                        registerObject.put("Topic14",text14);
                        registerObject.put("Topic15",text15);
                        registerObject.put("Topic16",text16);
                        registerObject.put("Topic17",text17);

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
