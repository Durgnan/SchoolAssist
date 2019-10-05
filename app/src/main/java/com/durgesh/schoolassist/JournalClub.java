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

public class JournalClub extends AppCompatActivity implements View.OnClickListener{
    ImageButton unhappy,neutral,happy,vhappy;
    Bundle bundle;
    String username;
    Spinner[] inputs = new Spinner[11];
    Button button;
    List<String> list=new ArrayList<>();
    String feedback="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_club);
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
            startActivity(new Intent(JournalClub.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        inputs[0]=findViewById(R.id.jc1);
        inputs[1]=findViewById(R.id.jc2);
        inputs[2]=findViewById(R.id.jc3);
        inputs[3]=findViewById(R.id.jc4);
        inputs[4]=findViewById(R.id.jc5);
        inputs[5]=findViewById(R.id.jc6);
        inputs[6]=findViewById(R.id.jc7);
        inputs[7]=findViewById(R.id.jc8);
        inputs[8]=findViewById(R.id.jc9);
        inputs[9]=findViewById(R.id.jc10);
        inputs[10]=findViewById(R.id.jc11);

        unhappy=findViewById(R.id.jcunhappy);
        neutral=findViewById(R.id.jcneutral);
        happy=findViewById(R.id.jchappy);
        vhappy=findViewById(R.id.jcvhappy);
        unhappy.setOnClickListener(feedbackController);
        neutral.setOnClickListener(feedbackController);
        happy.setOnClickListener(feedbackController);
        vhappy.setOnClickListener(feedbackController);



        ImageButton imageButton=findViewById(R.id.jcimg);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JournalClub.this,Pop1.class));
            }
        });
        addItemsOnSpinner();


        button=findViewById(R.id.jc_submit);
        button.setOnClickListener(this);


    }
    void  addItemsOnSpinner()
    {
        List<String> list = new ArrayList<>();
        list.add("1");list.add("2");list.add("3");list.add("4");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int i=0;i<11;i++)
            inputs[i].setAdapter(branchAdapter);

    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<11;i++)
            list.add(i,inputs[i].getSelectedItem().toString().trim());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("JournalClub");
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
                        ParseObject registerObject = new ParseObject("JournalClub");
                        registerObject.put("username", username);
                        JSONArray array = new JSONArray(list);
                        EditText editText=findViewById(R.id.commentjc);
                        if(editText.getText().toString().trim()==null ||editText.getText().toString().trim()=="")
                            registerObject.put("comments","");
                        else
                            registerObject.put("comments",editText.getText().toString().trim());
                        registerObject.put("Score",array);
                        registerObject.put("feedback",feedback);
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

    View.OnClickListener feedbackController = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch(id)
            {
                case R.id.jcunhappy:
                    feedback="UNHAPPY";
                    unhappy.setImageResource(R.drawable.unhappy_green);
                    neutral.setImageResource(R.drawable.neutral_red);
                    happy.setImageResource(R.drawable.happy_red);
                    vhappy.setImageResource(R.drawable.vhappy_red);


                    break;
                case R.id.jcneutral:
                    feedback="NEUTRAL";
                    unhappy.setImageResource(R.drawable.unhappy_red);
                    neutral.setImageResource(R.drawable.neutral_green);
                    happy.setImageResource(R.drawable.happy_red);
                    vhappy.setImageResource(R.drawable.vhappy_red);
                    break;
                case R.id.jchappy:
                    feedback="HAPPY";
                    unhappy.setImageResource(R.drawable.unhappy_red);
                    neutral.setImageResource(R.drawable.neutral_red);
                    happy.setImageResource(R.drawable.happy_green);
                    vhappy.setImageResource(R.drawable.vhappy_red);
                    break;
                case R.id.jcvhappy:
                    feedback="VERY HAPPY";
                    unhappy.setImageResource(R.drawable.unhappy_red);
                    neutral.setImageResource(R.drawable.neutral_red);
                    happy.setImageResource(R.drawable.happy_red);
                    vhappy.setImageResource(R.drawable.vhappy_green);
                    break;
            }
        }
    };
}
