package com.djl.netUtils;

import com.djl.androidutils.DJLUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by djl on 2016/3/16.
 */
public class NetTask {
    public static int timeOut = 5;// 单位秒
    private static OkHttpClient mClient;

    public static void init() {
        if (mClient == null)
            mClient = new OkHttpClient().newBuilder().connectTimeout(timeOut, TimeUnit.SECONDS).build();
    }

    public static void request(Request request, Callback callBack) {
        init();
//        DJLUtils.log("request > url = "+request.url());
        Call call = mClient.newCall(request);
        call.enqueue(new CallBackWeaker(callBack));
    }

    private static class CallBackWeaker implements Callback {
        private WeakReference<Callback> callbackWeakReference;

        public CallBackWeaker(Callback callBack) {
            callbackWeakReference = new WeakReference<>(callBack);
        }

        @Override
        public void onFailure(Call call, IOException e) {
            if (callbackWeakReference.get() != null)
                try {
                    callbackWeakReference.get().onFailure(call, e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (callbackWeakReference.get() != null)
                callbackWeakReference.get().onResponse(call, response);
//            response.
        }

    }

    public static interface MyCallBack extends Callback {
        boolean isActivityClosed();
    }
}
