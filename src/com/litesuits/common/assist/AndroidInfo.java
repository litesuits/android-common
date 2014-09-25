package com.litesuits.common.assist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author MaTianyu
 * @date 2014-09-25
 */
public class AndroidInfo {

    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------  系统信息  " + time +"  --------------------");
        sb.append("\nBOARD        :" + android.os.Build.BOARD);
        sb.append("\nDEVICE       :" + android.os.Build.DEVICE);
        sb.append("\nMANUFACTURER :" + android.os.Build.MANUFACTURER);
        sb.append("\nPRODUCT      :" + android.os.Build.PRODUCT);
        sb.append("\nCODENAME     :" + android.os.Build.VERSION.CODENAME);
        sb.append("\nRELEASE      :" + android.os.Build.VERSION.RELEASE);
        sb.append("\nSDK          :" + android.os.Build.VERSION.SDK);
        return sb.toString();
    }

}
