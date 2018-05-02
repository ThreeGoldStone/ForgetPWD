package com.djl.androidutils.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.djl.androidutils.DJLUtils;
import com.djl.androidutils.MyApplication;
import com.djl.androidutils.SystemTool;
import com.umeng.analytics.MobclickAgent;

/**
 * @author DJL E-mail:
 * @version 1.0
 * @date 2015-6-25 上午9:34:44
 * @parameter
 */
public class SActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics screenSize = SystemTool.getScreenSize(this);
        DJLUtils.log(screenSize);
        if (getApp() != null) {
            getApp().getActivityManager().addActivity(this);
        }
    }

    public void finish_() {
        try {
            getApp().getActivityManager().finishActivity(this);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish_();
    }

    public MyApplication getApp() {
        return (MyApplication) getApplication();
    }
}
