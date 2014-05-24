package com.Data;


import android.app.Application;
import android.content.Context;

public class ApplicationTrans extends Application {
    private String value;
	private static Context context;
	
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
	
    public static Context getContext() {
        return context;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}