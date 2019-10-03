package com.durgesh.schoolassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spUserType;
    EditText etEmail,etPassword;
    Button btnLogin,btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        spUserType = findViewById(R.id.spUserType);
        addItemsOnSpinner();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String userType = spUserType.getSelectedItem().toString().trim();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("LoginDetails");
                query.whereEqualTo("email", email);
                query.whereEqualTo("password",password);
                query.whereEqualTo("userType",userType);
                try {
                    List<ParseObject> value = query.find();
                    if (value.size() == 1) {

                        if(userType.equals("Student"))
                        {
                            Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,StudentActivity.class).putExtra("user",email));
                            finish();
                        }
                        else if(userType.equals("Teacher"))
                        {
                            Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,TeacherRoot.class));
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Login Failed\n Please Try Again!",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));

            }
        });



    }

    public void addItemsOnSpinner(){
        List<String> list = new ArrayList<String>();
        list.add("Student");
        list.add("Teacher");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserType.setAdapter(branchAdapter);

    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        finish();
    }
}