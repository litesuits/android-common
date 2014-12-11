package com.litesuits.common.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.litesuits.android.log.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author MaTianyu
 * @date 2014-09-25
 */
public class AndroidUtil {
    private static final String TAG = AndroidUtil.class.getSimpleName();

    public static String getMacAddress(Context context) {
        //wifi mac地址
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        Log.i(TAG, " MAC：" + mac);
        return mac;
    }

    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------  系统信息  " + time + "  --------------------");
        sb.append("\nBOARD        :" + android.os.Build.BOARD);
        sb.append("\nDEVICE       :" + android.os.Build.DEVICE);
        sb.append("\nPRODUCT      :" + android.os.Build.PRODUCT);
        sb.append("\nMANUFACTURER :" + android.os.Build.MANUFACTURER);
        sb.append("\nCODENAME     :" + android.os.Build.VERSION.CODENAME);
        sb.append("\nRELEASE      :" + android.os.Build.VERSION.RELEASE);
        //sb.append("\nSDK          :" + android.os.Build.VERSION.SDK);
        return sb.toString();
    }

}
