package com.djl.forgetpwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.javaUtils.StringUtils;

public class NewPWDActivity extends MyActivity {


    private String picPath;

    @Override
    public void initData() {
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        picPath = intent.getStringExtra("picPath");
        if (!StringUtils.isEmpty(content)) {
            vFinder.getEditText(R.id.etContent).setText(content);
            vFinder.getEditText(R.id.etContent).setSelection(content.length());
        }
    }


    @Override
    public int contentLayoutId() {
        return R.layout.activity_new_pwd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPreview:
                String content = vFinder.getEditText(R.id.etContent).getText().toString();
                if (StringUtils.isEmpty(App.getInstance().pwd)) {
                    showError("请在上个页面先设置默认密码");
                    return;
                }

                LargePicActivity.start(this, content, picPath);
                break;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NewPWDActivity.class);
        context.startActivity(intent);
    }


    public static void start(Context context, String content, String picPath) {
        Intent intent = new Intent(context, NewPWDActivity.class);
        intent.putExtra("content", content);
        intent.putExtra("picPath", picPath);
        context.startActivity(intent);
    }
}
