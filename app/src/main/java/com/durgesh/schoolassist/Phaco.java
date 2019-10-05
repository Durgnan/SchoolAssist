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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Phaco extends AppCompatActivity implements View.OnClickListener{
    Bundle bundle;
    String username;
    Spinner[] inputs=new Spinner[20];
    Button button;
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phaco);
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
            startActivity(new Intent(Phaco.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        inputs[0]=findViewById(R.id.phaco1);
        inputs[1]=findViewById(R.id.phaco2);
        inputs[2]=findViewById(R.id.phaco3);
        inputs[3]=findViewById(R.id.phaco4);
        inputs[4]=findViewById(R.id.phaco5);
        inputs[5]=findViewById(R.id.phaco6);
        inputs[6]=findViewById(R.id.phaco7);
        inputs[7]=findViewById(R.id.phaco8);
        inputs[8]=findViewById(R.id.phaco9);
        inputs[9]=findViewById(R.id.phaco10);
        inputs[10]=findViewById(R.id.phaco11);
        inputs[11]=findViewById(R.id.phaco12);
        inputs[12]=findViewById(R.id.phaco13);
        inputs[13]=findViewById(R.id.phaco14);
        inputs[14]=findViewById(R.id.phaco15);
        inputs[15]=findViewById(R.id.phaco16);
        inputs[16]=findViewById(R.id.phaco17);
        inputs[17]=findViewById(R.id.phaco18);
        inputs[18]=findViewById(R.id.phaco19);
        inputs[19]=findViewById(R.id.phaco20);
        ImageButton imageButton=findViewById(R.id.phacoimg);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage(" Novice (score=2)\n Beginner (score=3) \n Advanced Beginner (score=4)\n Competent (score=5)")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Info");
                alert.show();
            }
        });
        addItemsOnSpinner();


        button=findViewById(R.id.phaco_submit);
        button.setOnClickListener(this);


    }
    void  addItemsOnSpinner()
    {
        List<String> list = new ArrayList<String>();
        list.add("0");list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int i=0;i<20;i++)
            inputs[i].setAdapter(branchAdapter);

    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<20;i++)
            list.add(i,inputs[i].getSelectedItem().toString().trim());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Phaco");
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
                        ParseObject registerObject = new ParseObject("Phaco");
                        registerObject.put("username", username);
                        EditText editText=findViewById(R.id.commentphaco);
                        if(editText.getText().toString().trim()==null ||editText.getText().toString().trim()=="")
                            registerObject.put("comments","");
                        else
                            registerObject.put("comments",editText.getText().toString().trim());
                        JSONArray array = new JSONArray(list);
                        registerObject.put("Score",array);
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
