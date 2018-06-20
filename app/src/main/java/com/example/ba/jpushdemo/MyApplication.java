package com.example.ba.jpushdemo;

import android.app.Application;

import com.example.ba.jpushdemo.cookie_tool.PersistentCookieJar;
import com.example.ba.jpushdemo.cookie_tool.cache.SetCookieCache;
import com.example.ba.jpushdemo.cookie_tool.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;

/**
 * Created by BA on 2018/6/20 0020.
 */

public class MyApplication extends Application {
    public static OkHttpClient client;
    @Override
    public void onCreate() {
        super.onCreate();

        //设置cookie保持自动登陆
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(cookieJar);
        client = builder.build();

    }
}
