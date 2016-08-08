package com.litesuits.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.litesuits.android.log.Log;

import java.util.ArrayList;

/**
 * @author MaTianyu
 * @date 2014-11-19
 */
public class NotificationUtil {
    private static int LedID = 0;
    private static final String TAG = NotificationUtil.class.getSimpleName();

    public static void notification(Context context, Uri uri,
                                    int icon, String ticker, String title, String msg) {
        Log.i(TAG, "notiry uri :" + uri);
        // 设置通知的事件消息
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            intent.setPackage(context.getPackageName());
        }
        intent.setData(uri);
        notification(context, intent, 0, icon, ticker, title, msg);
    }

    public static void notification(Context context, String activityClass, Bundle bundle,
                                    int icon, String ticker, String title, String msg) {
        // 设置通知的事件消息
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            intent.setPackage(context.getPackageName());
        }
        intent.putExtras(bundle);
        intent.setComponent(new ComponentName(context.getPackageName(), activityClass));
        notification(context, intent, 0, icon, ticker, title, msg);
    }

    public static void notification(Context context, Intent intent, int id,
                                    int icon, String ticker, String title, String msg) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification(context, pendingIntent, id, icon, ticker, title, msg);
    }

    public static void notification(Context context, PendingIntent pendingIntent, int id,
                                    int icon, String ticker, String title, String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(icon);

            builder.setContentTitle(title);
            builder.setTicker(ticker);
            builder.setContentText(msg);

            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setLights(0xFFFFFF00, 0, 2000);
            builder.setVibrate(new long[]{0, 100, 300});
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            Notification baseNF;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                baseNF = builder.getNotification();
            } else {
                baseNF = builder.build();
            }
            //发出状态栏通知
            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            nm.notify(id, baseNF);
        } else {
            // 创建一个NotificationManager的引用
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            // 定义Notification的各种属性
            Notification notification = new Notification(icon, ticker, System.currentTimeMillis());
            notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_SHOW_LIGHTS;
            notification.defaults = Notification.DEFAULT_ALL;
            notification.ledARGB = Color.GREEN;
            notification.ledOnMS = 5000; //闪光时间，毫秒

            notification.tickerText = ticker;
            notification.setLatestEventInfo(context, title, msg, pendingIntent);
            // 把Notification传递给NotificationManager
            notificationManager.notify(id, notification);
        }
    }

    public static void lightLed(Context context, int colorOx, int durationMS) {
        lightLed(context, colorOx, 0, durationMS);
    }

    public static void lightLed(Context context, int colorOx, int startOffMS, int durationMS) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.ledARGB = colorOx;
        notification.ledOffMS = startOffMS;
        notification.ledOnMS = durationMS;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        LedID++;
        nm.notify(LedID, notification);
        nm.cancel(LedID);
    }

    public static void lightLed(final Context context, final int colorOx, final int startOffMS, final int durationMS,
                                int repeat) {
        if (repeat < 1) {
            repeat = 1;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        for (int i = 0; i < repeat; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lightLed(context, colorOx, startOffMS, durationMS);
                }
            }, (startOffMS + durationMS) * i);
        }
    }

    public static void lightLed(Context context, ArrayList<LightPattern> patterns) {
        if (patterns == null) {
            return;
        }
        for (LightPattern lp : patterns) {
            lightLed(context, lp.argb, lp.startOffMS, lp.durationMS);
        }
    }

    public static class LightPattern {
        public int argb = 0;
        public int startOffMS = 0;
        public int durationMS = 0;

        public LightPattern(int argb, int startOffMS, int durationMS) {
            this.argb = argb;
            this.startOffMS = startOffMS;
            this.durationMS = durationMS;
        }
    }

}
