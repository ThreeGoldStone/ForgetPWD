package com.djl.forgetpwd;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.dummy.DummyContent;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

public class MainActivity extends MyActivity implements ListFragment.OnListFragmentInteractionListener {

    private static final int REQUEST_CODE = 111;
    public String pwd;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        pwd = getIntent().getStringExtra("pwd");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl, ListFragment.newInstance(3)).commit();
        SetOnClick(R.id.btNew);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNew:
                DJLUtils.log("new ");
                new LFilePicker()
                        .withActivity(this)
                        .withRequestCode(REQUEST_CODE)
                        .withTitle("文件选择")
                        .withIconStyle(Constant.ICON_STYLE_BLUE)
                        .withBackIcon(Constant.BACKICON_STYLETHREE)
                        .withNotFoundBooks("至少选择一个文件")
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
                break;
            case R.id.menu_setting:
                SettingActivity.start(this);
                break;
            case R.id.menu_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        DJLUtils.toastS(this, item.toString());
//        LargePicActivity.start(this, "");
    }

    public static void start(Context context, String pwd) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("pwd", pwd);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pwd = null;
    }
}
