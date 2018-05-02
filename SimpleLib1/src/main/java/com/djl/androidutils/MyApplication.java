package com.djl.androidutils;

import android.app.Application;

import com.djl.fileUtil.OKHttpFileDownLoader;
import com.djl.fileUtil.bitmaputil.BitmapLoader;
import com.djl.fileUtil.database.MyFileDownLoadDataBase;

public class MyApplication extends Application {
    private KJActivityManager mActivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityManager = KJActivityManager.create();
        MyFileDownLoadDataBase.init(this.getApplicationContext());
    }

    public KJActivityManager getActivityManager() {
        return mActivityManager;
    }

    public void exit() {
        getActivityManager().finishAllActivity();
        OKHttpFileDownLoader.getInstance().close();
        BitmapLoader.getInstance().close();
        MyFileDownLoadDataBase.getInstance().close();
    }

}
