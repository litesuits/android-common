package com.litesuits.common.assist;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import com.litesuits.android.log.Log;

/**
 * <!-- 解锁 -->
 * require <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
 *
 * @author MaTianyu
 * @date 2014-12-12
 */
public class KeyguardLock {
    KeyguardManager              mKeyguardManager;
    KeyguardManager.KeyguardLock mKeyguardLock;

    public KeyguardLock(Context context, String tag) {
        //获取系统服务
        mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        //初始化键盘锁，可以锁定或解开键盘锁
        mKeyguardLock = mKeyguardManager.newKeyguardLock(tag);
    }

    /**
     * Call requires API level 16
     */
    public boolean isKeyguardLocked() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Log.e("Log : ", "can not call isKeyguardLocked if SDK_INT < 16 ");
            return false;
        } else {
            return mKeyguardManager.isKeyguardLocked();
        }
    }

    /**
     * Call requires API level 16
     */
    public boolean isKeyguardSecure() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Log.e("Log : ", "can not call isKeyguardSecure if SDK_INT < 16 ");
            return false;
        } else {
            return mKeyguardManager.isKeyguardSecure();
        }
    }

    public boolean inKeyguardRestrictedInputMode() {
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public void disableKeyguard() {
        mKeyguardLock.disableKeyguard();
    }

    public void reenableKeyguard() {
        mKeyguardLock.reenableKeyguard();
    }

    public void release() {
        if (mKeyguardLock != null) {
            //禁用显示键盘锁定
            mKeyguardLock.reenableKeyguard();
        }
    }
}
