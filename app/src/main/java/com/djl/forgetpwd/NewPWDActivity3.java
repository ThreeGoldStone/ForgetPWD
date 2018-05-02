package com.djl.forgetpwd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.djl.androidutils.DJLUtils;
import com.djl.androidutils.adapter.MyAdapter;
import com.djl.forgetpwd.bean.PictureContentBean;
import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.javaUtils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewPWDActivity3 extends MyActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private String picPath;
    private String content;
    private Gson gson;
    public PictureContentBean mBean = new PictureContentBean();

    @Override
    public void initData() {

        gson = new GsonBuilder().create();
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        picPath = intent.getStringExtra("picPath");
        DJLUtils.log(content);
        DJLUtils.log(picPath);
        if (!StringUtils.isEmpty(content)) {
            try {
                mBean = gson.fromJson(content, PictureContentBean.class);
            } catch (Exception e) {
                e.printStackTrace();
                // Json 解析失败  用纯文本打开
                NewPWDActivity.start(this, content, picPath);
                finish_();
                return;
            }
        }
        if (mBean == null) {
            showError("无效的数据");
            finish_();
            return;
        }
        getSupportActionBar().setTitle("编辑 > " + mBean.getTitle());
        vFinder.getListView(R.id.lvTitleList).setAdapter(new MyAdapter<PictureContentBean>(mBean.getBeans(), this) {
            @Override
            protected void setView(HashMap<Integer, View> holder, PictureContentBean item, int position) {
                ((TextView) holder.get(android.R.id.text1)).setText(item.getTitle());
            }

            @Override
            protected ViewWithTag initConvertView() {
                return getViewWithTag(android.R.layout.simple_list_item_1, android.R.id.text1);
            }
        });
        vFinder.getListView(R.id.lvTitleList).setOnItemClickListener(this);
        vFinder.getListView(R.id.lvTitleList).setOnItemLongClickListener(this);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.transfer_detail2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_input_type:
//                String optionText = item.getTitle().toString();
//                if (optionText.equals(getString(R.string.menu_input_type_input))) {
//                    this.isEdit = false;
//                    item.setTitle(getString(R.string.menu_input_type_scan));
//                    vFinder.getEditText(R.id.etDaJianDanHao).setEnabled(false);
//                    vFinder.getEditText(R.id.etDaJianDanHaoHide).requestFocus();
//                    vFinder.getEditText(R.id.etDaJianDanHao).setHint("请扫描大包号");
//                } else {
//                    this.isEdit = true;
//                    vFinder.getEditText(R.id.etDaJianDanHao).setEnabled(true);
//                    vFinder.getEditText(R.id.etDaJianDanHao).requestFocus();
//                    vFinder.getEditText(R.id.etDaJianDanHao).setHint("请扫描或输入大包号");
//                    item.setTitle(getString(R.string.menu_input_type_input));
//                }
//                DJLUtils.log("isEdit = " + isEdit);
//                vFinder.getEditText(R.id.etDaJianDanHao).setText("");
//                break;
//            case R.id.menu_refresh:
//                // 调用接口查询该批次的数据,覆盖本地数据
//                sendDataByBatchNumber();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public int contentLayoutId() {
        return R.layout.activity_new_pwd3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSave:
                if (StringUtils.isEmpty(App.getInstance().pwd)) {
                    showError("请在上个页面先设置默认密码");
                    return;
                }
                content = gson.toJson(mBean);
                DJLUtils.log(content);

                LargePicActivity.start(this, content, picPath);
                break;
            case R.id.btNewTitle:
                final AutoCompleteTextView editText = new AutoCompleteTextView(this);
                new AlertDialog.Builder(this).setTitle("请输入标题").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editText.getText().toString();
                        if (StringUtils.isEmpty(title)) {
                            DJLUtils.toastS(NewPWDActivity3.this, "请输入有效的标题 (；°○° ) ！");
                        } else {
                            PictureContentBean pictureContentBean = new PictureContentBean().setTitle(title);
                            mBean.getBeans().add(pictureContentBean);
                            refreshDataView();
                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NewPWDActivity3.class);
        context.startActivity(intent);
    }


    public static void start(Context context, String content, String picPath) {
        Intent intent = new Intent(context, NewPWDActivity3.class);
        intent.putExtra("content", content);
        intent.putExtra("picPath", picPath);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewPWDDetailActivity3.start(this, position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new android.support.v7.app.AlertDialog.Builder(this).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"编辑", "删除"}), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        edittextTitle(position);
                        break;
                    case 1:
                        deleteTitleConform(position);
                        break;
//                    case 2:
//                        break;
                }
//                DJLUtils.toastS(getApplicationContext(), "" + which);
                dialog.dismiss();
            }
        }).create().show();
        return true;
    }

    private void deleteTitleConform(final int position) {
        PictureContentBean pictureContentBean = mBean.getBeans().get(position);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("确认要删除 “" + pictureContentBean.getTitle() + "“ 及其子内容 ？")
                .setConfirmText("确认").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                mBean.getBeans().remove(position);
                refreshDataView();
                sweetAlertDialog.dismiss();
            }
        }).setCancelText("点错了 ！").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        }).show();
    }

    private void edittextTitle(final int position) {
        final EditText editText = new EditText(NewPWDActivity3.this);
        new AlertDialog.Builder(NewPWDActivity3.this).setTitle("请输入标题").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editText.getText().toString();
                if (StringUtils.isEmpty(title)) {
                    DJLUtils.toastS(NewPWDActivity3.this, "请输入有效的标题 (；°○° ) ！");
                } else {
                    mBean.getBeans().get(position).setTitle(title);
                    refreshDataView();
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
//        PictureContentBean pictureContentBean = mBean.getBeans().get(position);
//        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE).setEditTextText(pictureContentBean.getTitle())
//                .setConfirmText("save").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                String title = sweetAlertDialog.getEditTextText();
//                if (StringUtils.isEmpty(title)) {
//                    DJLUtils.toastS(NewPWDActivity3.this, "请输入有效的标题 (；°○° ) ！");
//                } else {
//                    mBean.getBeans().get(position).setTitle(title);
//                    refreshDataView();
//                    sweetAlertDialog.dismiss();
//                }
//
//            }
//        }).setCancelText("cancel").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismiss();
//            }
//        }).show();
    }

    private void refreshDataView() {
        ((MyAdapter) vFinder.getListView(R.id.lvTitleList).getAdapter()).notifyDataSetChanged();
    }
}
