package com.litesuits.common.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.litesuits.android.log.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author MaTianyu
 * @date 2014-09-25
 */
public class TelephoneUtil {

    private static final String TAG = TelephoneUtil.class.getSimpleName();

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        Log.i(TAG, " IMEI：" + imei);
        return imei;
    }

    /**
     * 获取手机信息
     */
    public static String printTelephoneInfo(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------  手机信息  " + time + "  --------------------");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        //IMSI前面三位460是国家号码，其次的两位是运营商代号，00、02是中国移动，01是联通，03是电信。
        String providerName = null;
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providerName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providerName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providerName = "中国电信";
            }
        }
        sb.append(providerName + "  手机号：" + tm.getLine1Number() + " IMSI是：" + IMSI);
        sb.append("\nDeviceID(IMEI)       :" + tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion:" + tm.getDeviceSoftwareVersion());
        sb.append("\ngetLine1Number       :" + tm.getLine1Number());
        sb.append("\nNetworkCountryIso    :" + tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator      :" + tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName  :" + tm.getNetworkOperatorName());
        sb.append("\nNetworkType          :" + tm.getNetworkType());
        sb.append("\nPhoneType            :" + tm.getPhoneType());
        sb.append("\nSimCountryIso        :" + tm.getSimCountryIso());
        sb.append("\nSimOperator          :" + tm.getSimOperator());
        sb.append("\nSimOperatorName      :" + tm.getSimOperatorName());
        sb.append("\nSimSerialNumber      :" + tm.getSimSerialNumber());
        sb.append("\ngetSimState          :" + tm.getSimState());
        sb.append("\nSubscriberId         :" + tm.getSubscriberId());
        sb.append("\nVoiceMailNumber      :" + tm.getVoiceMailNumber());
        return sb.toString();

    }
}
