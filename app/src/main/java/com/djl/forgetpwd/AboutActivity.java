package com.djl.forgetpwd;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.djl.androidutils.DJLUtils;
import com.djl.androidutils.SystemTool;
import com.djl.forgetpwd.simple_successor.MyActivity;

public class AboutActivity extends MyActivity {


    private boolean isMoneyOn;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.llWelcome, R.id.llEmail, R.id.llqq, R.id.lldashang);
        String appVersion = SystemTool.getAppVersion(this);
        vFinder.getTextView(R.id.tvVersion).setText("当前版本 " + appVersion);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llWelcome:
                SplashActivity.start(this);
                break;
            case R.id.llEmail:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(vFinder.getTextView(R.id.tvEmail).getText().toString());
                DJLUtils.toastS(this, "已复制到剪切板 ");
                break;
            case R.id.llqq:
                ClipboardManager cm2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm2.setText(vFinder.getTextView(R.id.tvQQ).getText().toString());
                DJLUtils.toastS(this, "已复制到剪切板 ");
                break;
            case R.id.lldashang:
                setMoneyOn(true);
                break;
        }

    }

    private void setMoneyOn(boolean isMoneyOn) {
        vFinder.getView(R.id.activity_setting).setVisibility(isMoneyOn ? View.GONE : View.VISIBLE);
        vFinder.getView(R.id.ivMoney).setVisibility(isMoneyOn ? View.VISIBLE : View.GONE);
        this.isMoneyOn = isMoneyOn;
    }

    @Override
    public void onBackPressed() {
        if (isMoneyOn) {
            setMoneyOn(false);
        } else {
            super.onBackPressed();
        }
    }
}
