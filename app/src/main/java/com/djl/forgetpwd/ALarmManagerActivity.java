package com.djl.forgetpwd;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.forgetpwd.util.OneShotAlarm;

import java.util.Calendar;

public class ALarmManagerActivity extends MyActivity {

    private static final int REQUEST_CODE = 111;
    private AlertDialog pwdDialog;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_alarmmanager;
    }

    @Override
    public void initData() {
        SetOnClick(R.id.btNew);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNew:
                Intent intent = new Intent(ALarmManagerActivity.this, OneShotAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        ALarmManagerActivity.this, 0, intent, 0);

                // We want the alarm to go off 10 seconds from now.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 10);

                // Schedule the alarm!
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                break;
        }
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, ALarmManagerActivity.class);
        context.startActivity(intent);
    }
}
