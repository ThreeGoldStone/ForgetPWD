package com.djl.forgetpwd;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.djl.forgetpwd.bean.NameValuePairBean;
import com.djl.forgetpwd.simple_successor.MyActivity;

public class NewPWDEditActivity extends MyActivity {

    private static final int requestCode = 8889;
    private NameValuePairBean mBean;
    private int mIndex;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_new_pwdedit;
    }

    @Override
    public void initData() {
        mBean = (NameValuePairBean) getIntent().getSerializableExtra("data");
        mIndex = getIntent().getIntExtra("index", -1);
        if (mBean == null || mIndex < 0) {
            finish_();
        }
        vFinder.getEditText(R.id.etInputName).setText(mBean.getName());
        vFinder.getEditText(R.id.etInputContent).setText(mBean.getValue());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 112, 1, "save");
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static void start(MyActivity activity, NameValuePairBean bean, int index) {
        Intent intent = new Intent(activity, NewPWDEditActivity.class);
        intent.putExtra("data", bean);
        intent.putExtra("index", index);
        activity.startActivityForResult(intent, requestCode);
    }
}
