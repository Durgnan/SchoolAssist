package com.durgesh.schoolassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.durgesh.schoolassist.Adapters.RecyclerAdapter;
import com.durgesh.schoolassist.Adapters.StudentData;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {
    Bundle bundle;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        bundle = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.teacher_recycler);
        List<StudentData> list = new ArrayList<>();
        list = getData();
        recyclerAdapter = new RecyclerAdapter(list,getApplication(),"");
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherActivity.this));


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
            startActivity(new Intent(TeacherActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private List<StudentData> getData() {
        final List<StudentData> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Student");
        query.orderByAscending("roll");
        ParseObject val;
        try{
            List<ParseObject> value = query.find();
            for(int i=0;i<value.size();i++)
            {
                val = value.get(i);
                list.add(new StudentData(val.getString("username"),val.getString("email")));
            }
        }
        catch (ParseException p)
        {
            p.printStackTrace();
        }
        return list;
    }

}
