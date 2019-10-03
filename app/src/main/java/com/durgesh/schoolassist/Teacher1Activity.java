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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.File;

public class Teacher1Activity extends AppCompatActivity {
    AlertDialog.Builder builder;
    Button button,upload;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher1);
        button = findViewById(R.id.addquestions);
        builder = new AlertDialog.Builder(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        upload=findViewById(R.id.uploadquestions);
        textView=findViewById(R.id.filename);
        textView.setText("");
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
    public void chooseFile(){
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
        FilePickerDialog dialog = new FilePickerDialog(Teacher1Activity.this,properties);
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
            startActivity(new Intent(Teacher1Activity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
