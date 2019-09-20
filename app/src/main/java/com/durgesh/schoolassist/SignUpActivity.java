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

public class SignUpActivity extends AppCompatActivity {

    Spinner spUserType;
    Button btnRegister;
    EditText etUsername,etEmail,etPhoneNo,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        spUserType = findViewById(R.id.spUserType);
        List<String> list = new ArrayList<String>();
        list.add("Student");
        list.add("Teacher");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserType.setAdapter(branchAdapter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phoneNo = etPhoneNo.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String userType = spUserType.getSelectedItem().toString().trim();

                if(isAlreadyPresent(email))
                {
                    Toast.makeText(getApplication(),"Email Already Registered", Toast.LENGTH_LONG).show();
                }
                else {
                    addLoginDetails(email, password, userType);
                    addData(username, email, phoneNo, password, userType);
                }

            }
        });





    }
    public void addLoginDetails(String email,String password,String userType)
    {
        ParseObject registerObject = new ParseObject("LoginDetails");
        registerObject.put("email", email);
        registerObject.put("password", password);
        registerObject.put("userType",userType);

        registerObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                if (e != null)
                    Log.e("ERROR", e.getMessage());
                else {
                    Log.e("STATUS", "SUCCESS");
                }
            }
        });
    }
    public void addData(String username,String email,String phoneNo,String password,String userType){
        ParseObject registerObject = new ParseObject(userType);
        registerObject.put("username", username);
        registerObject.put("email", email);
        registerObject.put("phoneNumber", phoneNo);
        registerObject.put("password", password);

        registerObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                if (e != null)
                    Log.e("ERROR", e.getMessage());
                else {
                    Log.e("STATUS", "SUCCESS");
                    Toast.makeText(getApplicationContext(),"Registration Successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }

    boolean isAlreadyPresent(String email){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("LoginDetails");
        query.whereEqualTo("email", email);

        try {
            List<ParseObject> value = query.find();
            if (value.size() == 1)
                return true;
            else
                return false;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }
}