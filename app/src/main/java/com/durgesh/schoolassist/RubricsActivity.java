package com.durgesh.schoolassist;

import android.content.Intent;
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

import com.durgesh.schoolassist.Adapters.StudentData;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.sql.Struct;
import java.util.List;

public class RubricsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rubrics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        topic = findViewById(R.id.topics);
        score = findViewById(R.id.score);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,topics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,scores);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        score.setAdapter(adapter1);
        remarks = findViewById(R.id.remarks);
        button = findViewById(R.id.submit);
        button.setOnClickListener(this);


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
         startActivity(new Intent(RubricsActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
         finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View v) {

        int sc  = Integer.parseInt(score.getSelectedItem().toString());
        String rem = String.valueOf(remarks.getText());
        Log.e("Message",sc+" "+rem);
        if (sc<=3)
        {
            Log.e("Message","test1");
            if (rem.equals("") ||rem.equals(null) )
            {
                Toast.makeText(getApplicationContext(),"Please Enter Remarks",Toast.LENGTH_LONG).show();
                Log.e("Message","test2");
                return;

            }
           // else continue outer;


        }
            Log.e("Message","test3");
          ParseQuery<ParseObject> query = ParseQuery.getQuery("RubricsTopic");
            query.whereEqualTo("username",username);
            try{
                List<ParseObject> value = query.find();
                if(value.size()<=10)
                {
                    String seltopic = topic.getSelectedItem().toString();
                    int selScore = Integer.parseInt(score.getSelectedItem().toString());
                    String remark = (selScore<3)?String.valueOf(remarks.getText()):"";
                    ParseObject parseObject = new ParseObject("RubricsForm");
                    parseObject.put("username",username);
                    parseObject.put("topic",seltopic);
                    parseObject.put("score",selScore+"");
                    parseObject.put("remarks",remark);
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null)
                            {
                                Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Save Unsuccessful",Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Maximum Topic Limit Reached!!",Toast.LENGTH_LONG).show();
                }
            }
            catch (ParseException p)
            {
                p.printStackTrace();
            }



    }
    String[] topics = {
            "Select Anyone"
            ,"Draping"
            ,"Scleral access and Cauterization"
            ,"Sclerocorneal Tunnel"
            ,"Corneal Entry"
            , "Paracentesis and Viscoelastic Insertion"
            ,"Capsulorrhexis: Commencement of Flap & follow-through."
            ,"Capsulorrhexis: Formation and Circular Completion."
            ,"Hydrodissection: Visible Fluid Wave and Free prolapse of one pole of the nucleus."
            ,"Prolapse of nucleus completely into AC."
            ,"Nucleus Extraction"
            ,"Irrigation and Aspiration Technique with adequate removal of cortex"
            ,"Lens Insertion, Rotation, and Final position of intraocular Lens"
            ,"Wound Closure (Including Suturing, Hydration, and Checking Security as Required)"
            ,"Global Indices Wound Neutrality and Minimizing Eye Rolling and Corneal Distortion"
            ,"Eye Positioned Centrally With in Microscope View"
            ,"Conjunctival and Corneal Tissue Handling"
            ,"Intraocular Spatial awareness"
            ,"Iris Protection"
            ,"Overall Speed and Fluidity of Procedure"};
    String[] scores = {"0","1","2","3","4","5"};
    Spinner topic,score;
    EditText remarks;
    Button button;
    Bundle bundle;
    String username;

}
