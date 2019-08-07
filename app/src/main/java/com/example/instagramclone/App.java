package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("yaTymk8u4PZw1KuoGiy9yJUyop4vCdI5YnhsfGSo")
                .clientKey("5myf1NHyw3Ms2ZvTa7lFFxHbyeyZk0OFRpW5Y8LS")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
