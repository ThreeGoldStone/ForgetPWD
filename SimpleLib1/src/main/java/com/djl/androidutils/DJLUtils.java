package com.djl.androidutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Selection;
import android.text.Spannable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplelib.R;

import java.io.Closeable;

/**
 * @author DJL Since 2015.5.13
 */
public class DJLUtils {
    public static boolean DEBUG = true;
    public static String TAG = "djl";

    public static long toShortTime = 3000, time;

    public static boolean notTooFast(String message) {
        long currentTime = System.currentTimeMillis();
        long timeDec = currentTime - time;
        if (timeDec < toShortTime && lastMessage.equals(message)) {
            return false;
        } else {
            time = currentTime;
            lastMessage = message;
            return true;
        }
    }

    static long time_toofast = 0;

    /**
     * @param time 最短时间间隔
     * @return
     */
    public static boolean tooFast(int time) {
        long currentTime = System.currentTimeMillis();
        long timeDec = currentTime - time_toofast;
        if (timeDec < time) {
            return true;
        } else {
            time_toofast = currentTime;
            return false;
        }
    }

    static String lastMessage = "";

    public static void toastS(Context c, String message) {
//        Toast.makeText(c.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        if (notTooFast(message)) {
            toastLongCENTER(c, message);

        }
    }

    public static void toastL(Context c, String message) {
        Toast.makeText(c.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void toastLongCENTER(Context c, String message) {
        if (message == null || c == null) return;
        Toast result = new Toast(c.getApplicationContext());
        LayoutInflater inflate = (LayoutInflater)
                c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.layout_toast, null);
        ((TextView) v.findViewById(R.id.tvToast)).setText(message);
        result.setView(v);
        result.setDuration(10000);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.show();
//        Toast toast = Toast.makeText(c,
//                message, 10000);
//        toast.setGravity(Gravity.TOP, 0, 0);
//        LinearLayout toastView = (LinearLayout) toast.getView();
//        toastView.setOrientation(LinearLayout.HORIZONTAL);
////        toastView.addView();
////        toastView.setPadding();
//        View child = new View(c);
//        child.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
//        toastView.addView(child);
//        toastView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        toastView.setPadding(0, 50, 0, 50);
////        toastView.addView(child);
//        toast.show();
    }

    public static void log(Object message) {
        if (DEBUG) {
            Log.i(TAG, message.toString());
        }
    }

    /**
     * 显示dialog
     *
     * @param context  环境
     * @param strTitle 标题
     * @param strText  内容
     * @param icon     图标
     */
    public static void showDialog(Activity context, String strTitle, String strText, int icon) {
        try {
            AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
            tDialog.setIcon(icon);
            tDialog.setTitle(strTitle);
            tDialog.setMessage(strText);
            tDialog.setPositiveButton("确定", null);
            tDialog.show();
        } catch (Exception e) {

        }
    }

    /**
     * 显示dialog
     *
     * @param context 环境
     *                标题
     *                内容
     *                图标
     * @return
     */
    public static AlertDialog showDialog(Activity context, View layout, boolean cancelable) {
        try {
            AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
            tDialog.setCancelable(cancelable);
            AlertDialog Dialog = tDialog.create();
            Dialog.setView(layout, 0, 0, 0, 0);
            Dialog.show();
            return Dialog;
        } catch (Exception e) {

        }
        return null;
    }

    //
    // show the progress bar.

    /**
     * 显示进度条
     *
     * @param context       环境
     * @param title         标题
     * @param message       信息
     * @param indeterminate 确定性
     * @param cancelable    可撤销
     * @return
     */
    public static ProgressDialog showProgress(Context context, CharSequence title,
                                              CharSequence message, boolean indeterminate, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        if (title != null) {
            dialog.setTitle(title);
        }
        if (message != null) {
            dialog.setMessage(message);
        }
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    public static boolean safeClose(Closeable c) {
        try {
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 把et内文本光标移动到index
     *
     * @param et
     * @param index -1是最后
     * @return
     */
    public static void moveFoucs(EditText et, int index) {
        CharSequence charSequence = et.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, index < 0 ? charSequence.length() : index);
        }
    }

    public static int getHeight(int width, Context context) {
        DisplayMetrics screenSize = SystemTool.getScreenSize(context);
        return Math.round(width * screenSize.heightPixels / 1280);
    }

    public static int getWidth(int width, Context context) {
        DisplayMetrics screenSize = SystemTool.getScreenSize(context);
        return Math.round(width * screenSize.widthPixels / 720);
    }
}
