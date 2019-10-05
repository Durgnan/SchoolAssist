package com.durgesh.schoolassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1,button2,button3;
    Bundle bundle;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
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
            startActivity(new Intent(QuizActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    void init()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button1=findViewById(R.id.quiz_seminar);
        button2=findViewById(R.id.quiz_club);
        button3=findViewById(R.id.quiz_case);
        bundle=getIntent().getExtras();
        username = bundle.getString("username");
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.quiz_seminar:
                startActivity(new Intent(QuizActivity.this,SeminarEvaluation.class).putExtra("username",username));
                break;
            case R.id.quiz_club:
                startActivity(new Intent(QuizActivity.this,JournalClub.class).putExtra("username",username));
                break;
            case R.id.quiz_case:
                startActivity(new Intent(QuizActivity.this,CasePresentationForTheoryRubrics.class).putExtra("username",username));
                break;
        }
    }
}
