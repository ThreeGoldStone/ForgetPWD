package com.djl.forgetpwd.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by DJl on 2016/12/20.
 * email:1554068430@qq.com
 */

public class utils {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
//    public static SimpleDateFormat dateFormat4 = new SimpleDateFormat(" yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> LinkedList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type listType = new TypeToken<LinkedList<T>>() {
        }.getType();
        Gson gson = new Gson();
        LinkedList<T> arrayList = gson.fromJson(json, listType);
        return arrayList;
    }

    static long lastClickTime = 0;

    /**
     * 避免误操作
     *
     * @return
     */
    public static boolean isTooFast() {
        boolean result;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < 200) {
            result = true;
        } else {
            result = false;
        }
        lastClickTime = currentTimeMillis;
        return result;
    }

    public static String getTime() {
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime1() {
        return dateFormat1.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime2() {
        return dateFormat2.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime3() {
        return dateFormat3.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime4() {
        return dateFormat4.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 转全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 复制文本到剪切板
     *
     * @param context
     * @param label   显示的标示
     * @param text
     */
    public static void putTextIntoClip(Context context, CharSequence label, CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText(label, text);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
    }

//    public static void setInputKeyBoard(Context context, boolean show, EditText et) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (show)
//            imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
//        else {
////            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
//            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
//        }
//    }
}
