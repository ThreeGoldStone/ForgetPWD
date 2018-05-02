package com.djl.netUtils;

import android.content.Context;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface PublicNetConnectHandler {
    /**
     * 处理共性网络异常
     *
     * @param tag
     * @return
     */
    boolean netError(Context context, SimpleTag tag, Exception e);

    /**
     * 处理共性错误结果
     *
     * @param tag
     * @param response
     * @return
     */
    boolean errorCode(Context context, SimpleTag tag, String response);

    boolean parseError(Context context, SimpleTag tag, String response);
}
