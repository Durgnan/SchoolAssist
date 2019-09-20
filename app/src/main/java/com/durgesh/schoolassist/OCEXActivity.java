package com.durgesh.schoolassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class OCEXActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String username;
    Button interview,exam,interpersonal,casep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocex);

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
            startActivity(new Intent(OCEXActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        interview = findViewById(R.id.interview_s);
        interpersonal = findViewById(R.id.interpersonal_s);
        casep = findViewById(R.id.casep);
        exam = findViewById(R.id.exam);
        interview.setOnClickListener(this);
        interpersonal.setOnClickListener(this);
        exam.setOnClickListener(this);
        casep.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.interview_s:startActivity(new Intent(OCEXActivity.this,Interview.class).putExtra("username",username));break;
            case R.id.casep:startActivity(new Intent(OCEXActivity.this,CasePresentation.class).putExtra("username",username));break;
            case R.id.exam:startActivity(new Intent(OCEXActivity.this,Exam.class).putExtra("username",username));break;
            case R.id.interpersonal_s:startActivity(new Intent(OCEXActivity.this,Interpersonal.class).putExtra("username",username));break;
        }

    }
}
