package com.trax;

import android.app.Application;
import android.content.Context;

/**
 * Created by unautre on 27/11/14.
 */
public class Trax extends Application {
    private static Trax instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext(){
        return instance;
    }
}
