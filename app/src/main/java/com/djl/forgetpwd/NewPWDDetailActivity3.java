package com.djl.forgetpwd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.adapter.MyPwdDetailRecyclerViewAdapter;
import com.djl.forgetpwd.bean.PictureContentBean;
import com.djl.forgetpwd.simple_successor.App;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.forgetpwd.util.utils;
import com.djl.forgetpwd.view.RecyclerViewItemDecoration;
import com.djl.javaUtils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewPWDDetailActivity3 extends MyActivity implements MyPwdDetailRecyclerViewAdapter.OnItemClickListener {


    private Gson gson;
    private PictureContentBean mBean;
    private int index;
    private RecyclerView mRecyclerView;

    @Override
    public void initData() {

        gson = new GsonBuilder().create();
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        if (index < 0) {
            finish_();
            return;
        } else {
            mBean = App.getInstance().getActivityManager().findActivity(NewPWDActivity3.class).mBean.getBeans().get(index);
            if (mBean == null) {
                finish_();
                return;
            }
        }
        mRecyclerView = findViewById(R.id.recyclerViewNewPWDDetail3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, 0, 20, 0, 0));
        mRecyclerView.setAdapter(new MyPwdDetailRecyclerViewAdapter(mBean.getBeans(), this));
    }

    @Override
    public int contentLayoutId() {
        return R.layout.activity_new_pwd_detail3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btSave:
//                if (StringUtils.isEmpty(App.getInstance().pwd)) {
//                    showError("请在上个页面先设置默认密码");
//                    return;
//                }
//                content = gson.toJson(mBean);
//                LargePicActivity.start(this, content, picPath);
//                break;
            case R.id.btNewTitle:
                final EditText editText = new EditText(this);
                new AlertDialog.Builder(this).setTitle("请输入标题").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editText.getText().toString();
                        if (StringUtils.isEmpty(title)) {
                            DJLUtils.toastS(NewPWDDetailActivity3.this, "请输入有效的标题 (；°○° ) ！");
                        } else {
                            PictureContentBean pictureContentBean = new PictureContentBean().setTitle(title).setEdit(true);
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


    public static void start(Context context, int index) {
        Intent intent = new Intent(context, NewPWDDetailActivity3.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }


    private void refreshDataView() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClick(MyPwdDetailRecyclerViewAdapter.ViewHolder holder, int position, View clickView) {
        DJLUtils.log("click view   = " + clickView.getId());
        PictureContentBean mItem = holder.mItem;
        switch (clickView.getId()) {

            case R.id.btEdit:
                boolean enabled = mItem.isEdit();
                if (enabled) {

                } else {
                    // 非编辑状态改为编辑状态，并把content复制给TemporaryContent
                    mItem.setEdit(true).setTemporaryContent(mItem.getContent());
                    refreshDataView();
                }
                break;
            case R.id.btCopy:
                CharSequence text = holder.tvTitle.getText();
                utils.putTextIntoClip(this, text, holder.mContent.getText());
                DJLUtils.toastS(this, "已经复制 “" + text + "”的内容到剪切板");
                break;
            case R.id.tvSave:
                mItem.setEdit(false).setContent(holder.mContent.getText().toString().trim()).setTemporaryContent("");
                refreshDataView();
                break;
            case R.id.btCancel:
                mItem.setEdit(false).setTemporaryContent("");
                refreshDataView();
                break;
            case R.id.btDelete:
                deleteTitleConform(position);
                break;
        }
    }

    @Override
    public void onItemLongClick(MyPwdDetailRecyclerViewAdapter.ViewHolder holder, final int position, View clickView) {
//        switch (clickView.getId()) {
//            case R.id.llItemNewPwdDetails:
//                new android.support.v7.app.AlertDialog.Builder(this).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"编辑", "删除"}), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                edittextTitle(position);
//                                break;
//                            case 1:
//                                deleteTitleConform(position);
//                                break;
////                    case 2:
////                        break;
//                        }
//                        DJLUtils.toastS(getApplicationContext(), "" + which);
//                        dialog.dismiss();
//                    }
//                }).create().show();
//                break;
//    }
    }

    //
    private void deleteTitleConform(final int position) {
        PictureContentBean pictureContentBean = mBean.getBeans().get(position);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("确认要删除 “" + pictureContentBean.getTitle() + "“ 及其内容 ？")
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
//
//    private void edittextTitle(final int position) {
//        final EditText editText = new EditText(NewPWDDetailActivity3.this);
//        new AlertDialog.Builder(NewPWDDetailActivity3.this).setTitle("请输入标题").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String title = editText.getText().toString();
//                if (StringUtils.isEmpty(title)) {
//                    DJLUtils.toastS(NewPWDDetailActivity3.this, "请输入有效的标题 (；°○° ) ！");
//                } else {
//                    mBean.getBeans().get(position).setTitle(title);
//                    refreshDataView();
//                    dialog.dismiss();
//                }
//            }
//        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        }).create().show();
//    }
}
