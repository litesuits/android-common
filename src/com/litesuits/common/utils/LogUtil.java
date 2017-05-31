package com.litesuits.common.utils;

/**
 *
 * Created by idhyt on 2015/11/27.
 * 
 * Usage :
 *        LogUtil.d("test"); default print log
 *        LogUtil.d("test", true); force print log
 *        LogUtil.d("test", false); force don't print log      
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.litesuits.BuildConfig;

public class LogUtil {

    private static String className;
    private static String methodName;
    private static int lineNumber;

    private LogUtil() {
    /* Protect from instantiations */
    }

    public static boolean debugEnabled() {
        return BuildConfig.DEBUG;
    }

    private static String createLog( String log ) {
        return "[" + methodName + ":" + lineNumber + "]" + log;
    }

    private static void getCallerInfo(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message, boolean... args) {
        if (args.length > 0 && !args[0]) {
            return; 
        }

        if (debugEnabled() || (args.length > 0 && args[0])) {
            getCallerInfo(new Throwable().getStackTrace());
            Log.e(className, createLog(message));
        }
    }

    public static void i(String message, boolean... args){
        if (args.length > 0 && !args[0]) {
            return; 
        }

        if (debugEnabled() || (args.length > 0 && args[0])) {
            getCallerInfo(new Throwable().getStackTrace());
            Log.i(className, createLog(message));
        }
    }

    public static void d(String message, boolean... args) {
        if (args.length > 0 && !args[0]) {
            return; 
        }

        if (debugEnabled() || (args.length > 0 && args[0])) {
            getCallerInfo(new Throwable().getStackTrace());
            Log.d(className, createLog(message));
        }
    }

    public static void v(String message, boolean... args) {
        if (args.length > 0 && !args[0]) {
            return; 
        }

        if (debugEnabled() || (args.length > 0 && args[0])) {
            getCallerInfo(new Throwable().getStackTrace());
            Log.v(className, createLog(message));
        }
    }

    public static void w(String message, boolean... args) {
        if (args.length > 0 && !args[0]) {
            return; 
        }

        if (debugEnabled() || (args.length > 0 && args[0])) {
            getCallerInfo(new Throwable().getStackTrace());
            Log.w(className, createLog(message));
        }
    }

    @TargetApi(11)
    public static void wtf(String message, boolean... args) {
        if (args.length > 0 && !args[0]) {
            return; 
        }

        if (debugEnabled() || (args.length > 0 && args[0])) {

            if (Build.VERSION.SDK_INT >= 11) {
                getCallerInfo(new Throwable().getStackTrace());
                Log.wtf(className, createLog(message));
            } else {
                getCallerInfo(new Throwable().getStackTrace());
                Log.d(className, Log.getStackTraceString(new Throwable()))
            }
        }

    }

}
