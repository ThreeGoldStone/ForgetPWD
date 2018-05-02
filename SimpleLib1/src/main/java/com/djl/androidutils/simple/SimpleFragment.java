package com.djl.androidutils.simple;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.djl.androidutils.DJLUtils;
import com.djl.androidutils.MyApplication;
import com.djl.netUtils.MessageShower;
import com.umeng.analytics.MobclickAgent;

/**
 * @author DJL E-mail:
 * @version 1.0
 * @date 2015-6-25 下午5:26:12
 * @parameter
 */
@SuppressLint({"HandlerLeak", "NewApi"})
public abstract class SimpleFragment extends Fragment implements OnClickListener, MessageShower {
    private String mTag;
    public String data;
    private View layout;
    public SimpleViewFinder vFinder;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        layout = initLayout(inflater, container, savedInstanceState);
        vFinder = new SimpleViewFinder(layout);
        initView();
        initData();
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName() + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName() + "");
    }

    @Override
    public void onDetach() {
        vFinder = null;
        super.onDetach();
    }

    protected MyApplication getApp() {
        return (MyApplication) getActivity().getApplication();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        DJLUtils.log(this.getClass().getName() + hidden);
        if (!hidden) {
            onShow();
        }
    }

    /*****************************************************
     * Quick
     *************************************/

    public <T extends Activity> T getActivity(Class<T> clazz) {
        return getApp().getActivityManager().findActivity(clazz);
    }

    /***************************************************** Quick *************************************/
    /**
     *
     */
    public abstract void onShow();

    public abstract View initLayout(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState);

    public View findViewById(int id) {
        if (layout == null) {
            return null;
        }
        return layout.findViewById(id);

    }

    ;
//
//    public WebView getWebView(int id) {
//        return (WebView) getView(id);
//    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 批量设置点击事件
     *
     * @param ids
     */
    public void SetOnClick(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            View view = vFinder.getView(ids[i]);
            if (view != null) {
                view.setClickable(true);
                view.setOnClickListener(this);
            }
        }
    }

//    public TextView getTextView(int id) {
//        return (TextView) getView(id);
//    }
//
//    public Button getButton(int id) {
//        return (Button) getView(id);
//    }
//
//    public EditText getEditText(int id) {
//        return (EditText) getView(id);
//    }
//
//    public ImageView getImageView(int id) {
//        return (ImageView) getView(id);
//    }
//
//    public CheckBox getCheckBox(int id) {
//        return (CheckBox) getView(id);
//    }
//
//    public GridView getGridView(int id) {
//        return (GridView) getView(id);
//    }
//
//    public ListView getListView(int id) {
//        return (ListView) getView(id);
//    }
//
//    public LinearLayout getLinearLayout(int id) {
//        return (LinearLayout) getView(id);
//    }
//
//    public RelativeLayout getRelativeLayout(int id) {
//        return (RelativeLayout) getView(id);
//    }
//
//    public TableLayout getTableLayout(int id) {
//        return (TableLayout) getView(id);
//    }
//
//    public RadioButton getRadioButton(int id) {
//        return (RadioButton) getView(id);
//    }
//
//    public RadioGroup getRadioGroup(int id) {
//        return (RadioGroup) getView(id);
//    }
//
//    public View getView(int id) {
//        if (mViews == null) {
//            mViews = new HashMap<>();
//        }
//        if ((!mViews.containsKey(id)) || mViews.get(id) == null) {
//            View view = findViewById(id);
//            mViews.put(id, view);
//        }
//        return mViews.get(id);
//    }

    public String getmTag() {
        return mTag;
    }

    public SimpleFragment setmTag(String mTag) {
        this.mTag = mTag;
        return this;
    }

    public String getData(String data) {
        this.data = data;
        return data;
    }

    /**
     * mHandler事件处理
     *
     * @param msg
     */
    public void handleMessage(Message msg) {

    }

    //    /*************************************************** <<<<<<<<<<<网络模块>>>>>>>>>>>>********************************************/
//    protected static final int onFail = 0xabcdef01;
//    protected static final int onResult = 0xabcdef02;
//    public Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case onFail:
//                    Object[] objsF = (Object[]) msg.obj;
//                    Call call = (Call) (objsF[0]);
//                    IOException eF = (IOException) objsF[1];
//                    SimpleTag tagF = (SimpleTag) call.request().tag();
//                    if (tagF != null) {
//                        //告诉接口调用结束
//                        onNetConnectFinish(tagF);
//                        onFailure(tagF, eF);
//                    }
//                    break;
//                case onResult:
//                    //告诉接口调用结束
//                    onNetConnectFinish((SimpleTag) ((Object[]) msg.obj)[0]);
//                    Object[] obj = (Object[]) msg.obj;
//                    onParseResult((SimpleTag) obj[0], (String) obj[1]);
//                    break;
//                default:
//                    SimpleFragment.this.handleMessage(msg);
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 通用的错误现实方式
//     */
//    public abstract void showError(String error);
//
//    public abstract void onParseResult(SimpleTag simpleTag, String result);
//
//    /**
//     * 无论请求是否成功，结束就会调这里
//     *
//     * @param tag
//     */
//    public abstract void onNetConnectFinish(SimpleTag tag);
//
//    @Override
//    public void onFailure(Call call, IOException e) {
//        mHandler.obtainMessage(onFail, new Object[]{call, e}).sendToTarget();
//    }
//
//    /**
//     * 网络问题
//     *
//     * @param tag
//     * @param e
//     * @return
//     */
//    protected abstract boolean onFailure(SimpleTag tag, IOException e);
//
//    @Override
//    public void onResponse(Call callR, Response response) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        sb.append("onResponse >>> url = " + callR.request().url())
//                .append("\nresponse.code() = " + response.code());
//        SimpleTag tag = (SimpleTag) callR.request().tag();
//        if (tag != null) {
//            switch (tag.type) {
//                case string_default:
//                    String result = response.body().string();
//                    sb.append("\nstring_default = " + result);
//                    mHandler.obtainMessage(onResult, new Object[]{tag, result}).sendToTarget();
//                    break;
//            }
//        }
//        DJLUtils.log(sb);
//    }
/**************************************************<<<<<<<<<<<网络模块>>>>>>>>>>>>********************************************/

}
