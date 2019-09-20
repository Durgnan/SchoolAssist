package com.durgesh.schoolassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.durgesh.schoolassist.Adapters.QuestionData;
import com.durgesh.schoolassist.Adapters.RecyclerAdapter;
import com.durgesh.schoolassist.Adapters.StudentData;
import com.durgesh.schoolassist.Adapters.qAdapter;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TakeQuizActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    Button button;
    EditText editText;
    ImageView imageView;
    AlertDialog.Builder builder;
    String user;
    qAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        button.setOnClickListener(this);



    }

    public void init()
    {

        setContentView(R.layout.activity_take_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               builder = new AlertDialog.Builder(this);

        //
        bundle=getIntent().getExtras();
        user = bundle.getString("user");
        button=findViewById(R.id.submitap);
        recyclerView = findViewById(R.id.recycler);
        //
        List<QuestionData> list = new ArrayList<>();
        list = getData();
        adapter = new qAdapter(list,getApplication(),user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TakeQuizActivity.this));

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
            startActivity(new Intent(TakeQuizActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
    private List<QuestionData> getData() {
        final List<QuestionData> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.orderByAscending("QuestionNO");
        ParseObject val;
        try{
            List<ParseObject> value = query.find();
            for(int i=0;i<value.size();i++)
            {
                val = value.get(i);
                list.add(new QuestionData(val.getInt("QuestionNO"),val.getParseFile("question")));
            }
        }
        catch (ParseException p)
        {
            p.printStackTrace();
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        builder.setMessage("Do you want to end the test ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        List<String> answers=adapter.getAnswers();
                        createObject(answers);
                        finish();

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
        alert.setTitle("AlertDialogExample");
        alert.show();









    }
    public void createObject(List<String> answers) {
        ParseObject entity = new ParseObject("Answers");
        //JSONArray array = (JSONArray) answers;
        entity.put("Answers", new JSONArray(answers));
        entity.put("username", user);

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        entity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
            }
        });
    }
}
