package com.djl.androidutils.simple;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.djl.netUtils.MessageShower;

import java.util.ArrayList;

/**
 * 使用注释的方式初始化ContentView ,View ,及view的点击事件
 *
 * @author DJL E-mail:
 * @version 1.0
 * @date 2015-6-25 下午12:01:57
 * @parameter
 */
@SuppressLint("HandlerLeak")
public abstract class SimpleActivity extends SActivity implements OnClickListener, MessageShower {
    private ArrayList<View> clickViews;
    private boolean isActivityClosed;
    public SimpleViewFinder vFinder;

    /**
     * mHandler事件处理
     *
     * @param msg
     */
    public void handleMessage(Message msg) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        isActivityClosed = true;
        if (clickViews != null) {
            for (View clickView : clickViews) {
                clickView.setOnClickListener(null);
            }
            clickViews.clear();
        }
        vFinder = null;
        super.onDestroy();
    }

    /*****************************************************
     * Quick
     *************************************/

    public <T extends Activity> T getActivity(Class<T> clazz) {
        return getApp().getActivityManager().findActivity(clazz);
    }

    /*****************************************************
     * Quick
     *************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initContent() > 0)
            setContentView(initContent());
        vFinder = new SimpleViewFinder(this);
        initView();
        initData();
    }

    /**
     * 初始化contentView
     *
     * @return
     */
    public abstract int initContent();

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

    /**
     * 批量设置点击事件
     *
     * @param views
     */
    public void SetOnClick(View... views) {
        if (clickViews == null)
            clickViews = new ArrayList<>();
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view != null) {
                view.setClickable(true);
                view.setOnClickListener(this);
                clickViews.add(view);

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
//    public ProgressBar getProgressBar(int id) {
//        return (ProgressBar) getView(id);
//    }
//
//    public ImageView getImageView(int id) {
//        return (ImageView) getView(id);
//    }
//
//    public ViewPager getViewPager(int id) {
//        return (ViewPager) getView(id);
//    }
//
//    public Spinner getSpinner(int id) {
//        return (Spinner) getView(id);
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
//    public WebView getWebView(int id) {
//        return (WebView) getView(id);
//    }

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

    //    /*************************************************** <<<<<<<<<<<网络模块>>>>>>>>>>>>********************************************/
//    protected static final int onFail = 0xabcdef01;
//
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
//                    SimpleActivity.this.handleMessage(msg);
//                    break;
//            }
//        }
//    };

//    public abstract void onParseResult(SimpleTag simpleTag, String result);
//
//    /**
//     * 无论请求是否成功，结束就会调这里
//     *
//     * @param tag
//     */
//    public abstract void onNetConnectFinish(SimpleTag tag);

//    @Override
//    public void onFailure(Call call, IOException e) {
//        mHandler.obtainMessage(onFail, new Object[]{call, e}).sendToTarget();
//    }

    /**
     * 网络问题
     *
     * @param tag
     * @param e
     * @return
     */
//    protected abstract boolean onFailure(SimpleTag tag, IOException e);

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
//
//    @Override
//    public boolean isActivityClosed() {
//        return isActivityClosed;
//    }
/**************************************************<<<<<<<<<<<网络模块>>>>>>>>>>>>********************************************/
}
