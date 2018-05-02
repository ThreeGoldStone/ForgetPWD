package com.djl.forgetpwd;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.javaUtils.StringUtils;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends MyActivity {
    private final int SDK_PERMISSION_REQUEST = 127;
    private static final int REQUEST_CODE = 111;
    private AlertDialog pwdDialog;
    private String permissionInfo;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.btNew, R.id.btOpen);
        setDefaultPwdDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPersimmions();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btNew:
//                DJLUtils.log("new ");
//                NewPWDActivity3.start(this);
//                break;
            case R.id.btNew:
                DJLUtils.log("new ");
                NewPWDConfigActivity.start(this);
                break;
            case R.id.btOpen:
                DJLUtils.log("Open ");
                new LFilePicker()
                        .withActivity(this)
                        .withRequestCode(REQUEST_CODE)
                        .withTitle("文件选择")
                        .withIconStyle(Constant.ICON_STYLE_BLUE)
                        .withBackIcon(Constant.BACKICON_STYLETHREE)
                        .withNotFoundBooks("至少选择一个文件")
                        .withMutilyMode(false)
                        .withRootPath(App.getInstance().getDefaultPicPath())
//                        .setDir(true)
                        //.withFileFilter(new String[]{"txt", "png", "docx"})
                        .start();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_default_pwd:
                setDefaultPwdDialog();
                break;
            case R.id.menu_setting:
                SettingActivity.start(this);
                break;
            case R.id.menu_about:
                AboutActivity.start(this);
                break;
            case R.id.menu_alarm:
                ALarmManagerActivity.start(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDefaultPwdDialog() {
        pwdDialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_default_pwd)
                .setTitle("设置默认密码")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etPwd = (EditText) pwdDialog.findViewById(R.id.etPWD);
                                App.getInstance().pwd = etPwd.getText().toString().trim();
                                DJLUtils.log(App.getInstance().pwd);
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                ).create();
        pwdDialog.show();
        EditText etPwd = (EditText) pwdDialog.findViewById(R.id.etPWD);
        String pwd = App.getInstance().pwd;
        if (!StringUtils.isEmpty(pwd)) {
            etPwd.setText(App.getInstance().pwd);
            etPwd.setSelection(pwd.length());
        }

    }


    public static void start(Context context) {
        Intent intent = new Intent(context, Main2Activity.class);
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
                if (!StringUtils.isEmpty(path)) {
                    LargePicActivity.start(this, path);
                    Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        App.getInstance().pwd = null;
        pwdDialog = null;
        super.onDestroy();
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            }
//            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
//            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
//                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
//            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
