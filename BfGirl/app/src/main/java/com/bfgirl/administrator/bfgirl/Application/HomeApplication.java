package com.bfgirl.administrator.bfgirl.Application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;

/**
 * Created by Administrator。 on 2017/5/20.
 */

public class HomeApplication extends Application {
    private static Context mAppContext;
    public void onCreate() {
        super.onCreate();
        mAppContext=this;
        OkGo.init(this);
        Fresco.initialize(this);
    }
}
