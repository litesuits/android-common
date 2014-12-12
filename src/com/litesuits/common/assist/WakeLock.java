package com.litesuits.common.assist;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import com.litesuits.android.log.Log;

/**
 * <!-- 亮屏 -->
 * require <uses-permission android:name="android.permission.WAKE_LOCK"/>
 *
 * @author MaTianyu
 * @date 2014-11-04
 */
public class WakeLock {
    PowerManager          pm;
    PowerManager.WakeLock wakeLock;

    public WakeLock(Context context, String tag) {
        ////获取电源的服务 声明电源管理器
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, tag);
    }

    /**
     * Call requires API level 7
     */
    public boolean isScreenOn() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR_MR1) {
            Log.e("Log : ", "can not call isScreenOn if SDK_INT < 7 ");
            return false;
        } else {
            return pm.isScreenOn();
        }
    }

    public void turnScreenOn() {
        //点亮亮屏
        Log.i("Log : ", "PowerManager.WakeLock : wakeLock.isHeld: " + wakeLock.isHeld());
        if (!wakeLock.isHeld()) {
            Log.i("Log : ", "PowerManager.WakeLock : 点亮屏幕");
            wakeLock.acquire();
        }
    }

    public void turnScreenOff() {
        //释放亮屏
        Log.i("Log : ", "PowerManager.WakeLock : wakeLock.isHeld: " + wakeLock.isHeld());
        if (wakeLock.isHeld()) {
            Log.i("Log : ", "PowerManager.WakeLock : 灭掉屏幕");
            wakeLock.release();
        }
    }

    protected void release() {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }
}
