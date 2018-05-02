package com.djl.netUtils;

import android.app.Activity;

/**
 * Created by DJl on 2017/2/9.
 * email:1554068430@qq.com
 */

public interface MessageShower {
    void showError(String message, Object... objects);

    void showProgress(boolean show, boolean cancelAble, String title, String content);

    Activity getActivity();
}
