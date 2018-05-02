package com.djl.androidutils;

import android.os.Handler;
import android.os.Looper;

public class MainHandler extends Handler {

    public MainHandler() {
        super(Looper.getMainLooper());
    }
}