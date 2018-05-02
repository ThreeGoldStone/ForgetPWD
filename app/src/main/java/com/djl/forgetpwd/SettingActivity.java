package com.djl.forgetpwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import java.util.List;

public class SettingActivity extends MyActivity {

    private static final int REQUEST_CODE = 111;


    @Override
    public int contentLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.llPicPath);
        vFinder.getTextView(R.id.tvPicPath).setText(App.getInstance().getDefaultPicPath());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
