package com.djl.forgetpwd.simple_successor;

import android.content.Context;
import android.os.CountDownTimer;

import com.djl.androidutils.simple.SimpleFragment;
import com.djl.forgetpwd.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by DJl on 2016/12/20.
 * email:1554068430@qq.com
 */

public abstract class MyFragment extends SimpleFragment {
    private SweetAlertDialog progress;
    private int i = -1;
    private CountDownTimer mCountDownTimer;

//    @Override
//    public void onParseResult(SimpleTag simpleTag, String result) {
//        if (StringUtils.isEmpty(result)) {
//            showError("接口返回为空！");
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
//
//    protected abstract void onSuccessResult(SimpleTag simpleTag, String result);
//
//    protected void onErrorResult(SimpleTag simpleTag, String ErrorMsg) {
//        // TODO errorResult
//    }

//    protected void parseError(SimpleTag simpleTag, String result) {
//        // TODO parse error
//    }

//    @Override
//    public void showError(String error) {
//        DJLUtils.toastS(getContext(), error);
//    }
//
//    @Override
//    protected boolean onFailure(SimpleTag tag, IOException e) {
//        showError(getString(R.string.connect_error_message));
//        return false;
//    }

    public void showProgress(boolean show, boolean cancelAble, String title, String content) {
        if (show) {
            if (progress == null) {
                progress = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE).setTitleText(title);
                progress.setCancelable(cancelAble);
            }
            progress.setContentText(content);
            if (!progress.isShowing()) {
                progress.show();
                mCountDownTimer = onCreatePregressDialog(getActivity());
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
