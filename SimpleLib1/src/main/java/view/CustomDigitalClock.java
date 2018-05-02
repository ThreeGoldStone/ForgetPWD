/* 
* Copyright (C) 2012 The * Project 
* All right reserved. 
* Version 1.00 2012-2-11 
* Author veally@foxmail.com 
*/
package view;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Custom digital clock
 *
 * @author veally@foxmail.com
 */
public class CustomDigitalClock extends TextView {
    Calendar mCalendar;
    private final static String m12 = "h:mm aa";
    private final static String m24 = "k:mm";
    private FormatChangeObserver mFormatChangeObserver;
    private Runnable mTicker;
    private Handler mHandler;
    private long endTime;
    public static long distanceTime;
    private ClockListener mClockListener;
    private static boolean isFirst;
    private boolean mTickerStopped;
    @SuppressWarnings("unused")
    private String mFormat;
    private String headText = "";


    public CustomDigitalClock(Context context) {
        super(context);
        initClock(context);
    }

    public CustomDigitalClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock(context);
    }

    private void initClock(Context context) {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
        mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(
                Settings.System.CONTENT_URI, true, mFormatChangeObserver);
        setFormat();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        start();
    }

    private void start() {
        mTickerStopped = false;
        mHandler = new Handler();
/**
 * requests a tick on the next hard-second boundary
 */
        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped)
                    return;
                long currentTime = System.currentTimeMillis();
                if (currentTime / 1000 == endTime / 1000 - 5 * 60) {
                    if (mClockListener != null) mClockListener.remainFiveMinutes();
                }
                distanceTime = endTime - currentTime;
                distanceTime /= 1000;
                if (distanceTime == 0) {
                    if (mClockListener != null) mClockListener.timeEnd();
                    onDetachedFromWindow();
                } else if (distanceTime < 0) {
                    if (mClockListener != null) mClockListener.timeEnd();
                    onDetachedFromWindow();
                } else {
                    setText(headText + dealTime(distanceTime));
                }
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
            }
        };
        mTicker.run();
    }

    /**
     * deal time string
     *
     * @param time
     * @return
     */
    public Spanned dealTime(long time) {
        Spanned str;
        StringBuffer returnString = new StringBuffer();
        long day = time / (24 * 60 * 60);
        long hours = (time % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
        String dayStr = String.valueOf(day);
        String hoursStr = timeStrFormat(String.valueOf(hours));
        String minutesStr = timeStrFormat(String.valueOf(minutes));
        String secondStr = timeStrFormat(String.valueOf(second));
        if (day > 0)
            returnString.append(dayStr).append("天 ");
        returnString.append(hoursStr).append(":")
                .append(minutesStr).append(":").append(secondStr).append("");
        str = Html.fromHtml(returnString.toString());
        int size = Math.round(getTextSize() * 0.7f);
        if (day > 0) {
            String d = day + "";
            int l = d.length();
            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), l, l + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
//        if (day >= 10) {
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 2, 3,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 5, 7,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 9, 11,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 13, 14,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 1, 2,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 4, 6,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 8, 10,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(size), 12, 13,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
// return returnString.toString(); 
        return str;
    }

    /**
     * format time
     *
     * @param timeStr
     * @return
     */
    private static String timeStrFormat(String timeStr) {
        switch (timeStr.length()) {
            case 1:
                timeStr = "0" + timeStr;
                break;
        }
        return timeStr;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    /**
     * Clock end time from now on.
     *
     * @param endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
        start();
    }

    public void setEndTime(long endTime, String headText) {
        this.headText = headText;
        this.endTime = endTime;
        start();
    }

    /**
     * Pulls 12/24 mode from system settings
     */
    private boolean get24HourMode() {
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }

    private void setFormat() {
        if (get24HourMode()) {
            mFormat = m24;
        } else {
            mFormat = m12;
        }
    }

    public void close() {
        mClockListener = null;
        getContext().getContentResolver().unregisterContentObserver(mFormatChangeObserver);
        mFormatChangeObserver = null;
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            setFormat();
        }
    }

    public void setClockListener(ClockListener clockListener) {
        this.mClockListener = clockListener;
    }

    public interface ClockListener {
        void timeEnd();

        void remainFiveMinutes();
    }
} 