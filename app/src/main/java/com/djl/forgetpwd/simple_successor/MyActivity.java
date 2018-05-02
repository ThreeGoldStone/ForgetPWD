package com.djl.forgetpwd.simple_successor;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.djl.androidutils.simple.SimpleActivity;
import com.djl.forgetpwd.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by DJl on 2016/12/20.
 * email:1554068430@qq.com
 */

public abstract class MyActivity extends SimpleActivity {
    private SweetAlertDialog progress;
    private int i = -1;
    private CountDownTimer mCountDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        } else if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().setStatusBarColor(0x00ffffff);
//        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initContent() {
        return R.layout.base_layout;
    }

    @Override
    public void initView() {
        View contentView = getLayoutInflater().inflate(contentLayoutId(), null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.FILL_VERTICAL;
        contentView.setLayoutParams(params);
        ((ViewGroup) vFinder.getView(R.id.main_content)).addView(contentView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public abstract int contentLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onParseResult(SimpleTag simpleTag, String result) {
//        if (StringUtils.isEmpty(result)) {
//            onErrorResult(simpleTag, "接口返回为空！");
//            return;
//        }
//        String code = simpleTag.code;
//        // 数据类型1
//        if (Setting.DataType1.isThisType(code)) {
//            try {
//                Setting.DataType1 type1 = Setting.DataType1.parse(result);
//                if (type1.HasError) {
//                    onErrorResult(simpleTag, type1.ErrorMsg);
//                } else {
//                    onSuccessResult(simpleTag, result);
//                }
//            } catch (JSONException e) {
//                parseError(simpleTag, result);
//                e.printStackTrace();
//            }
//
//        } else {
//            onParseOtherDataType(simpleTag, result);
//        }
//    }

//    public void onParseOtherDataType(SimpleTag simpleTag, String result) {
//        // TODO 其他特殊的数据格式
//    }

//    protected abstract void onSuccessResult(SimpleTag simpleTag, String result);

//    protected void onErrorResult(SimpleTag simpleTag, String ErrorMsg) {
//        showError(ErrorMsg);
//    }

//    protected void parseError(SimpleTag simpleTag, String result) {
//        showError("数据解析错误");
//    }


    @Override
    public void showError(String message, Object... objects) {
        AlertDialog errorDialog = new AlertDialog.Builder(this)
                .setTitle("错误")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
        errorDialog.show();
//        try {
//            new SweetAlertDialog(MyActivity.this, SweetAlertDialog.ERROR_TYPE)
//                    .setContentText(message)
//                    .setCancelText("cancel")
//                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            sweetAlertDialog.dismiss();
//                        }
//                    })
////                    .setConfirmText("重试")
////                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
////                        @Override
////                        public void onClick(SweetAlertDialog sweetAlertDialog) {
////                            sweetAlertDialog.dismiss();
////                        }
////                    })
//                    .show();
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//            DJLUtils.toastS(this, message);
//        }

    }

//    @Override
//    protected boolean onFailure(SimpleTag tag, IOException e) {
//        showError(getString(R.string.connect_error_message));
//        return false;
//    }

    public void showProgress(boolean show, boolean cancelAble, String title, String content) {
        if (show) {
            if (progress == null) {
                progress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText(title);
                progress.setCancelable(cancelAble);
            }
            progress.setContentText(content);
            if (!progress.isShowing()) {
                progress.show();
                mCountDownTimer = onCreatePregressDialog(this);
            } else {
                progress.setTitleText(title).setContentText(content);
            }
        } else {
            if (progress != null) {
                progress.dismiss();
            }
        }

    }

    /**
     * 设置进度条颜色变换
     *
     * @param context
     * @return
     */
    public CountDownTimer onCreatePregressDialog(final Context context) {

        return new CountDownTimer(800 * 30, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i % 6) {
                    case 0:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        progress.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            public void onFinish() {
                i = -1;
            }
        }.start();
    }
}
