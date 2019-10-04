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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Ptosis extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String username;
    Spinner[] inputs=new Spinner[17];
    Button button;
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptosis);
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
            startActivity(new Intent(Ptosis.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        inputs[0]=findViewById(R.id.ptosis1);
        inputs[1]=findViewById(R.id.ptosis2);
        inputs[2]=findViewById(R.id.ptosis3);
        inputs[3]=findViewById(R.id.ptosis4);
        inputs[4]=findViewById(R.id.ptosis5);
        inputs[5]=findViewById(R.id.ptosis6);
        inputs[6]=findViewById(R.id.ptosis7);
        inputs[7]=findViewById(R.id.ptosis8);
        inputs[8]=findViewById(R.id.ptosis9);
        inputs[9]=findViewById(R.id.ptosis10);
        inputs[10]=findViewById(R.id.ptosis11);
        inputs[11]=findViewById(R.id.ptosis12);
        inputs[12]=findViewById(R.id.ptosis13);
        inputs[13]=findViewById(R.id.ptosis14);
        inputs[14]=findViewById(R.id.ptosis15);
        inputs[15]=findViewById(R.id.ptosis16);
        inputs[16]=findViewById(R.id.ptosis17);
        addItemsOnSpinner();


        button=findViewById(R.id.ptosis_submit);
        button.setOnClickListener(this);


    }
    void  addItemsOnSpinner()
    {
        List<String> list = new ArrayList<String>();
        list.add("0");list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int i=0;i<17;i++)
            inputs[i].setAdapter(branchAdapter);

    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<17;i++)
            list.add(i,inputs[i].getSelectedItem().toString().trim());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ptosis");
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
                        ParseObject registerObject = new ParseObject("Ptosis");
                        registerObject.put("username", username);
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
