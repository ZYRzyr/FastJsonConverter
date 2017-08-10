package com.zyr.converter;

import android.app.Application;

import com.zyr.converter.network.ApiClient;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.getInstance().init();
    }
}
