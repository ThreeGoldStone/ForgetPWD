package com.djl.forgetpwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.bean.PictureContentBean;
import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.google.gson.GsonBuilder;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.io.File;
import java.util.List;

public class NewPWDConfigActivity extends MyActivity {

    private static final int REQUEST_CODE = 111;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_new_pwd_config;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.llPicPath, R.id.btNew);
        vFinder.getTextView(R.id.tvPicPath).setText(App.getInstance().getDefaultPicPath());
        vFinder.getEditText(R.id.etPWD).setText(App.getInstance().pwd);
        getSupportActionBar().setTitle("创建秘密");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNew:
                DJLUtils.log("new ");
                String fileName = vFinder.getEditText(R.id.etFileName).getText().toString().trim();
                String title = vFinder.getEditText(R.id.etTitle).getText().toString().trim();
                String pwd = vFinder.getEditText(R.id.etPWD).getText().toString().trim();
                if (StringUtils.isEmpty(pwd)) {
                    showError("加密密码不能为空 !");
                    return;
                }
                if (StringUtils.isEmpty(title)) {
                    showError("标题不能为空 !");
                    return;
                }
                if (StringUtils.isEmpty(fileName)) {
                    showError("文件名不能为空 !");
                    return;
                }
                App.getInstance().pwd = pwd;
                PictureContentBean pictureContentBean = new PictureContentBean().setTitle(title);
                NewPWDActivity3.start(this, new GsonBuilder().create().toJson(pictureContentBean), new File(App.getInstance().getDefaultPicPath(), fileName).getAbsolutePath());
                break;
            case R.id.llPicPath:
                new LFilePicker()
                        .withActivity(this)
                        .withRequestCode(REQUEST_CODE)
                        .withTitle("文件选择")
                        .withIconStyle(Constant.ICON_STYLE_BLUE)
                        .withBackIcon(Constant.BACKICON_STYLETHREE)
                        .withNotFoundBooks("至少选择一个文件")
                        .setDir(true)
//                        .withFileFilter(new String[]{"txt", "png", "docx"})
                        .start();
                break;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NewPWDConfigActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
                //for (String s : list) {
                //    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                //}
                String path = list.get(0);
                App.getInstance().setDefaultPicPath(path);
                vFinder.getTextView(R.id.tvPicPath).setText(path);
                Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
