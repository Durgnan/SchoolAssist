package com.durgesh.schoolassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.durgesh.schoolassist.Adapters.RecyclerAdapter;
import com.durgesh.schoolassist.Adapters.StudentData;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {
    Bundle bundle;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    Button button,upload;
    TextView textView;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        bundle = getIntent().getExtras();
        button = findViewById(R.id.addquestions);
        builder = new AlertDialog.Builder(this);
        upload=findViewById(R.id.uploadquestions);
        textView=findViewById(R.id.filename);
        textView.setText("");
        recyclerView = findViewById(R.id.teacher_recycler);
        List<StudentData> list = new ArrayList<>();
        list = getData();
        recyclerAdapter = new RecyclerAdapter(list,getApplication(),"");
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherActivity.this));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText()=="" || textView.getText()==null)
                {
                    Toast.makeText(getApplicationContext(),"No File Selected",Toast.LENGTH_LONG).show();

                }
                else
                {
                    createObject(textView.getText().toString());
                    textView.setText("");
                }
            }
        });



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
    public void chooseFile(){
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
        FilePickerDialog dialog = new FilePickerDialog(TeacherActivity.this,properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                Log.e("Message", files[0]);
                textView.setText(files[0]);
                          }
        });
        dialog.show();
    }
    public void createObject(final String path) {
        if(path.endsWith("jpg") || path.endsWith("JPG")||path.endsWith("png")||path.endsWith("PNG"))
        {
            builder.setMessage("Do you want to Upload the Question ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
                            int count=0;
                            try {
                                count=query.count();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            ParseObject entity = new ParseObject("Questions");

                            entity.put("QuestionNO",count+1 );
                            entity.put("question", new ParseFile(new File(path)));

                            // Saves the new object.
                            // Notice that the SaveCallback is totally optional!
                            entity.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    // Here you can handle errors, if thrown. Otherwise, "e" should be null
                                    if (e==null)
                                    {
                                        Toast.makeText(getApplicationContext(),"Question Uploaded Successfully!!",Toast.LENGTH_LONG).show();
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
        else
        {
            Toast.makeText(getApplicationContext(),"Wrong File Type",Toast.LENGTH_LONG).show();
        }

    }
}
