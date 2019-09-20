package com.durgesh.schoolassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PracticalsActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String username;
    Button ocex,opdc,opds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practicals);
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
            startActivity(new Intent(PracticalsActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        Log.e("Message",username);
        ocex = findViewById(R.id.ocex);
        opdc = findViewById(R.id.opdc);
        opds = findViewById(R.id.opds);
        ocex.setOnClickListener(this);
        opdc.setOnClickListener(this);
        opds.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ocex:startActivity(new Intent(PracticalsActivity.this,OCEXActivity.class).putExtra("username",username));break;
            case R.id.opdc:startActivity(new Intent(PracticalsActivity.this,OPDClinicalActivity.class).putExtra("username",username));break;
            case R.id.opds:startActivity(new Intent(PracticalsActivity.this,OPDSkills.class).putExtra("username",username));break;

        }

    }
}
