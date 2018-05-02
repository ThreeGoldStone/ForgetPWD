package com.djl.forgetpwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.forgetpwd.view.PWDView;
import com.djl.javaUtils.StringUtils;

public class LargePicActivity extends MyActivity {

    private String content;
    private String picPath;
    private PWDView pwdView;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_large_pic;
    }

    @Override
    public void initData() {
        pwdView = (PWDView) vFinder.getView(R.id.PWDView);
        SetOnClick(R.id.btSave, R.id.btShowContent);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        picPath = intent.getStringExtra("picPath");
        if (!StringUtils.isEmpty(picPath)) {
            String[] split = picPath.split("/");
            if (split.length > 0) {
                String name = split[split.length - 1];
                vFinder.getEditText(R.id.etFileName).setText(name);
                vFinder.getEditText(R.id.etFileName).setSelection(name.length());
            }
        }
        if (StringUtils.isEmpty(content)) {
            pwdView.loadImage(picPath);
            vFinder.getView(R.id.llSave).setVisibility(View.GONE);
            vFinder.getView(R.id.llShowContent).setVisibility(View.VISIBLE);
        } else {
            String pwd = App.getInstance().pwd;
            pwdView.setContent(content, pwd);
            vFinder.getView(R.id.llSave).setVisibility(View.VISIBLE);
            vFinder.getView(R.id.llShowContent).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSave:
                EditText editText = vFinder.getEditText(R.id.etFileName);
                String fileName = editText.getText().toString();
                if (StringUtils.isEmpty(fileName)) {
                    showError(editText.getHint().toString());
                    return;
                }
                if (!fileName.endsWith(".png")) fileName = fileName + ".png";
                pwdView.saveImage(App.getInstance().getDefaultPicPath() + "/" + fileName);
                DJLUtils.toastS(this, "保存成功!");
                App.getInstance().getActivityManager().finishOthersActivity(Main2Activity.class);
                break;
            case R.id.btShowContent:
                String pwd = App.getInstance().pwd;
                String content = null;
                try {
                    content = pwdView.getContent(pwd);
                    NewPWDActivity3.start(this, content, picPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    DJLUtils.toastS(this, "图片有误，或者密码错误！");
                }
                break;
        }

    }

    public static void start(Context context, String picPath) {
        Intent intent = new Intent(context, LargePicActivity.class);
        intent.putExtra("picPath", picPath);
        context.startActivity(intent);
    }

    public static void start(Context context, String content, String picPath) {
        Intent intent = new Intent(context, LargePicActivity.class);
        intent.putExtra("content", content);
        intent.putExtra("picPath", picPath);
        context.startActivity(intent);
    }
}
