package com.example.ireader.app;

import android.app.Application;

/**
 * Created by yajun on 2016/9/21.
 *
 */
public class App extends Application {

    static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
