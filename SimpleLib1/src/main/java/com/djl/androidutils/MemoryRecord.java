package com.djl.androidutils;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by DJl on 2016/5/1.
 * email:1554068430@qq.com
 */
public class MemoryRecord {
    public static boolean IS_DEBUG = true;
    //    ReferenceQueue queue = new ReferenceQueue();
    static ArrayList<WeakReference> prs = new ArrayList<>();

    public static void add(Object object) {
        log("++++++" + object.toString() + "++++++");
//        PhantomReference pr = new PhantomReference(object, queue);
        WeakReference pr = new WeakReference(object);
        prs.add(pr);
    }


    public static void log() {
        for (WeakReference pr : prs) {
            try {
                log("--------Current lift = " + (pr.get() == null ? "null" : pr.get().getClass().getSimpleName() == "" ? pr.get().getClass().getSuperclass().getSimpleName() : pr.get().getClass().getSimpleName()));
            } catch (Exception e) {
                log("null ex");
            }
        }
    }

    static void log(String l) {
        Log.i("MemoryRecord", l);
    }

}
