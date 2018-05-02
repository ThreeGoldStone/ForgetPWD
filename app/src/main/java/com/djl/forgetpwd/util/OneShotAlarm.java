package com.djl.forgetpwd.util;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.djl.androidutils.DJLUtils;

/**
 * Created by DJl on 2017/9/18.
 * email:1554068430@qq.com
 */
public class OneShotAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DJLUtils.toastS(context, "hahahah ");
//        new AlertDialog.Builder(context).setMessage(" hahahha   ").create().show();
    }
}
