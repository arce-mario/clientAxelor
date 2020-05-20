package com.uca.isi.axelor;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.tumblr.remember.Remember;

public class MyApplication extends MultiDexApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(getApplicationContext(), "com.uca.apps.isi.taken");
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
