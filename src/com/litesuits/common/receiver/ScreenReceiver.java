package com.litesuits.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.litesuits.android.log.Log;

public class ScreenReceiver extends BroadcastReceiver {
    private String TAG = "ScreenActionReceiver";
    private ScreenListener screenListener;

    public ScreenReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.d(TAG, "屏幕解锁广播...");
            if (screenListener != null) {
                screenListener.screenOn();
            }
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "屏幕加锁广播...");
            if (screenListener != null) {
                screenListener.screenOff();
            }
        }
    }

    public void registerScreenReceiver(Context context, ScreenListener screenListener) {
        try {
            this.screenListener = screenListener;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            Log.d(TAG, "注册屏幕解锁、加锁广播接收者...");
            context.registerReceiver(this, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterScreenReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
            Log.d(TAG, "注销屏幕解锁、加锁广播接收者...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static interface ScreenListener {
        public void screenOn();

        public void screenOff();
    }

}