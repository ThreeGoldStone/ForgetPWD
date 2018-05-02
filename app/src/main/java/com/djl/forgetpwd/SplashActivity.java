package com.djl.forgetpwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.djl.androidutils.SystemTool;
import com.djl.forgetpwd.simple_successor.MyActivity;

public class SplashActivity extends MyActivity {


    @Override
    public int contentLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
    }

    @Override
    public int initContent() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.btNext);
        String appVersion = SystemTool.getAppVersion(this);
        vFinder.getTextView(R.id.tvVersion).setText("不能忘的秘密 " + appVersion);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNext:
                Main2Activity.start(this);
                finish_();
                break;

        }
    }


}
