package com.djl.netUtils;

import android.os.Handler;
import android.os.Message;

import com.djl.androidutils.DJLUtils;
import com.djl.androidutils.MainHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by DJl on 2017/2/8.
 * email:1554068430@qq.com
 */

public abstract class StringNetWork implements NetWork {
    private State state = State.none;
    public Call mCall;
    public String result;
    protected MessageShower shower;
    protected String requestMessage = "提交中...";
    protected String progressMessage = "加载中...";
    protected String netErrorMessage = "请求服务器失败,请检查您的网络";

    public StringNetWork(MessageShower shower) {
        this.shower = shower;
    }


    @Override
    public State state() {
        return state;
    }

    protected void state(State state) {
        this.state = state;
        onStateChange();
    }

    @Override
    public void onStateChange() {
        switch (state) {
            case none:
                shower.showProgress(false, true, "", "");
                break;
            case request:
                shower.showProgress(true, true, "", requestMessage);
                break;
            case loading:
                shower.showProgress(true, true, "", progressMessage);
                break;
            case cancel:
                shower.showProgress(false, true, "", "");
                break;
            case fail:
                shower.showProgress(false, true, "", "");
                shower.showError(netErrorMessage, "netError", this);
                break;
            case serverError:
                shower.showProgress(false, true, "", "");
                shower.showError("服务器错误 500", "netError", this);
                break;
            case success:
                shower.showProgress(false, true, "", "");
                break;
        }
    }

    @Override
    public void request() throws Exception {
        mCall = mClient.newCall(newRequest());
        mCall.enqueue(this);
        state(State.request);
    }

    protected abstract Request newRequest() throws Exception;

    @Override
    public void cancel() {
        if (mCall != null) {
            if (!mCall.isCanceled()) {
                mCall.cancel();
                state(State.cancel);
            }
        }
    }

    //    /*************************************************** <<<<<<<<<<<网络模块>>>>>>>>>>>>********************************************/
    protected static final int onFail = 0xabcdef01;
    protected static final int onServerError = 0xabcdef04;
    protected static final int onResult = 0xabcdef02;
    protected static final int onLoading = 0xabcdef03;
    protected Handler mHandler = new MainHandler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case onFail:
//                    IOException eF = (IOException) msg.obj;
                    state(State.fail);
                    break;
                case onServerError:
//                    IOException eF = (IOException) msg.obj;
                    state(State.serverError);
                    break;
                case onLoading:
                    //告诉接口调用结束
                    state(State.loading);
                    break;
                case onResult:
                    //告诉接口调用结束
                    state(State.success);
                    break;
            }
        }
    };


    @Override
    public void onFailure(Call call, IOException e) {
        mHandler.obtainMessage(onFail).sendToTarget();
        DJLUtils.log("requestFail > " + "\nurl = " + call.request().url());
    }


    @Override
    public void onResponse(Call callR, Response response) throws IOException {
        int code = response.code();
        StringBuilder sb = new StringBuilder();
        switch (code) {
            case 404:
                mHandler.obtainMessage(onFail).sendToTarget();
                break;
            case 500:
            case 503:
                mHandler.obtainMessage(onServerError).sendToTarget();
                break;
            default:
                mHandler.obtainMessage(onLoading).sendToTarget();
                result = response.body().string();
                mHandler.obtainMessage(onResult, result).sendToTarget();
                break;
        }
        DJLUtils.log(sb.append("onResponse >>> url = " + callR.request().url())
                .append("\nresponse.code() = " + code)
                .append("\nstring_default = " + result));
    }

    /**************************************************
     * <<<<<<<<<<<网络模块>>>>>>>>>>>>
     ********************************************/

    protected static Request postJson(String url, String content) {
        DJLUtils.log("postJson > " +
                "\nurl = " + url +
                "\ncontent" + content
        );
        RequestBody body = RequestBody.create(NOMAL_JSON, content);
        return new Request.Builder().url(url).post(body).build();
    }

    protected static Request postForm(String url, Map<String, String> params) {
        String content = postParams(params, "utf-8");
        RequestBody body = RequestBody.create(NOMAL_MEDIATYPE, content);
        DJLUtils.log("postForm > " +
                "\nurl = " + url +
                "\ncontent" + content
        );
        return new Request.Builder().url(url).post(body).build();
    }

    protected static Request getRequest(String url) {
        DJLUtils.log("getRequest > " +
                "\nurl = " + url
        );
        return new Request.Builder().url(url).get().build();
    }

    protected static String postParams(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
}
