package com.djl.forgetpwd;

import android.util.DisplayMetrics;
import android.view.View;

import com.djl.androidutils.DJLUtils;
import com.djl.androidutils.SystemTool;
import com.djl.forgetpwd.simple_successor.MyActivity;

public class LoginActivity extends MyActivity {


    @Override
    public int contentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.btPWDSet, R.id.btToMain,R.id.btTest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPWDSet:
                String pwd = vFinder.getEditText(R.id.etPWD).getText().toString();
                MainActivity.start(this, pwd);
                finish_();
                break;
            case R.id.btToMain:
                MainActivity.start(this, "");
                finish_();
                break;
            case R.id.btTest:
                DisplayMetrics screenSize = SystemTool.getScreenSize(this);
                float density = screenSize.density;
                DJLUtils.toastS(this, "density");
                DJLUtils.log(density);
                break;
        }
    }
}
