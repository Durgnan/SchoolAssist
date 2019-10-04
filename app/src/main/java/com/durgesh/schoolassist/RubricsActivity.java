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
    Button button1,button2,button3,button4,button5,button6,button7,button8;
    Bundle bundle;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rubrics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        findViews();



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
    public boolean onSupportNavigateUp() {
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onBackPressed();
        return true;
    }

    void findViews() {
        button1=findViewById(R.id.lts);
        button2=findViewById(R.id.pcs);
        button3=findViewById(R.id.phaco);
        button4=findViewById(R.id.ptosis);
        button5=findViewById(R.id.sics);
        button6=findViewById(R.id.strabismus);
        button7=findViewById(R.id.trabeculectomy);
        button8=findViewById(R.id.vitrectomy);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.lts:startActivity(new Intent(RubricsActivity.this,LTS.class).putExtra("username",username));
                break;
            case R.id.pcs:startActivity(new Intent(RubricsActivity.this,PCS.class).putExtra("username",username));
                break;
            case R.id.phaco:startActivity(new Intent(RubricsActivity.this,Phaco.class).putExtra("username",username));
                break;
            case R.id.ptosis:startActivity(new Intent(RubricsActivity.this,Ptosis.class).putExtra("username",username));
                break;
            case R.id.sics:startActivity(new Intent(RubricsActivity.this,SICS.class).putExtra("username",username));
                break;
            case R.id.strabismus:startActivity(new Intent(RubricsActivity.this,Stram.class).putExtra("username",username));
                break;
            case R.id.trabeculectomy:startActivity(new Intent(RubricsActivity.this,Trab.class).putExtra("username",username));
                break;
            case R.id.vitrectomy:startActivity(new Intent(RubricsActivity.this,Vit.class).putExtra("username",username));
                break;
        }

    }
}
