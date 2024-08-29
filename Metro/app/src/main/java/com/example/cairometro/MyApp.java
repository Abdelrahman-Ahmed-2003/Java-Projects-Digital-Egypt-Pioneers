package com.example.cairometro;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApp extends Application {
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        MyApp.sharedPreferences = sharedPreferences;
    }
}
