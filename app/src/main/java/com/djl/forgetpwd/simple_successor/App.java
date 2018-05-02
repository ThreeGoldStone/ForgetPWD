package com.djl.forgetpwd.simple_successor;

import android.content.SharedPreferences;
import android.os.Environment;

import com.djl.androidutils.MyApplication;
import com.djl.javaUtils.StringUtils;

import java.io.File;

/**
 * Created by DJl on 2017/6/21.
 * email:1554068430@qq.com
 */

public class App extends MyApplication {
    public String pwd = "";
    private static App app;
    private SharedPreferences setting;
    private static String default_default_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecretsCantBeForgotten";

    @Override
    public void onCreate() {
        super.onCreate();
        setting = getSharedPreferences("setting", MODE_PRIVATE);

        app = this;
    }

    public String getDefaultPicPath() {
        String default_pic_path = setting.getString("default_pic_path", "");
        if (StringUtils.isEmpty(default_pic_path)) {
            setDefaultPicPath(default_default_path);
            return default_default_path;
        }
        return default_pic_path;
    }

    public void setDefaultPicPath(String path) {
        if (path != null) {
            File file = new File(path);
            boolean mkdirs = file.mkdirs();
            setting.edit().putString("default_pic_path", path).commit();
        }
    }

    public static App getInstance() {
        return app;
    }
}
