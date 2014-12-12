package com.litesuits.common.receiver;

import android.content.*;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import com.litesuits.android.log.Log;

import java.util.List;

/**
 * Call requires API level 4
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 *
 * @author MaTianyu
 * @date 14-7-23
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private SmsListener smsListener;

    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(TAG, "收到广播：" + intent.getAction());
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            if (pdus != null) {
                String msgBody = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    for (Object obj : pdus) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                        msgBody += sms.getMessageBody();
                    }
                }
                if (smsListener != null) {
                    smsListener.onMessage(msgBody);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerSmsReceiver(Context context, SmsListener smsListener) {
        try {
            this.smsListener = smsListener;
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterSmsReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static interface SmsListener {
        public void onMessage(String msg);
    }

    /**
     * Call requires API level 4
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     *
     * @param phone
     * @param msg
     */
    public void sendMsgToPhone(String phone, String msg) {
        Log.i(TAG, "发送手机：" + phone + " ,内容： " + msg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            SmsManager manager = SmsManager.getDefault();
            List<String> texts = manager.divideMessage(msg);
            for (String txt : texts) {
                manager.sendTextMessage(phone, null, txt, null, null);
            }
        }

    }

    /**
     * Call requires API level 4
     * <uses-permission android:name="android.permission.WRITE_SMS"/>
     *
     * @param context
     * @param phone
     * @param msg
     */
    public void saveMsgToSystem(Context context, String phone, String msg) {
        ContentValues values = new ContentValues();
        values.put("date", System.currentTimeMillis());
        //阅读状态 
        values.put("read", 0);
        //1为收 2为发  
        values.put("type", 2);
        values.put("address", phone);
        values.put("body", msg);
        context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
    }

}
