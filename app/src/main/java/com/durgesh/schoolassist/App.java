package com.durgesh.schoolassist;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("mD15HQMDy4aCOIZXkZ0tYRY35rxtqOalao2ylWHw")
                .clientKey("E5Z3YxJyYEHq34RNsTksrIw7Siqap1nOVRqp5au0")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
