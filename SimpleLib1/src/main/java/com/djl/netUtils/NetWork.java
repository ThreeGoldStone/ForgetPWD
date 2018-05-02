package com.djl.netUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by DJl on 2017/2/8.
 * email:1554068430@qq.com
 */

public interface NetWork extends Callback {
    MediaType NOMAL_MEDIATYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    MediaType NOMAL_JSON = MediaType.parse("application/json; charset=UTF-8");
    int timeOut = 5;// 单位秒
    OkHttpClient mClient = new OkHttpClient().newBuilder().connectTimeout(timeOut, TimeUnit.SECONDS).build();

    enum State {
        none,
        request,
        loading,
        cancel,
        fail,
        serverError,
        success
    }

    State state();

    void request() throws Exception;

    void cancel();

    void onStateChange();
}
