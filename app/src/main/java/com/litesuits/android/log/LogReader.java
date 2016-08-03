/*
 * Copyright (C) 2013 litesuits.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.litesuits.android.log;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 打印日志
 *
 * @author mty
 * @time 2011-11-2下午06:23:29
 */
public class LogReader extends Thread {
    public static final String TAG           = "LogReader";
    public static final String LOG_FILE_PATH = "/bonglog.txt";
    public static final String LOG_ROOT_PATH = "/sdcard";

    public static  boolean   open        = true;
    private static LogReader instance    = null;
    private static Process   mLogcatProc = null;

    private BufferedReader mReader = null;
    private String packageName = "*";

    public static void startCatchLog(String packageName) {
        if (!open) return;
        if (instance == null) {
            instance = new LogReader();
            instance.packageName = packageName;
            instance.start();
        }
    }

    public static void stopCatchLog() {
        if (!open) return;
        if (mLogcatProc != null) {
            mLogcatProc.destroy();
            mLogcatProc = null;
        }
    }

    @Override
    public void run() {
        Log.i(TAG, "log reader(catcher) is running..---------------------------");
        BufferedWriter bw = null;
        try {
            mLogcatProc = Runtime.getRuntime().exec("logcat " + packageName + ":I");
            mReader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));

            // 打印系统信息。
            logSystemInfo();

            String line;
            File file = new File(LOG_ROOT_PATH + LOG_FILE_PATH);
            if (file.exists() && isFileSizeOutof10M(file)) {
                file.delete();
            }
            if (file.exists()) {
                System.out.println("log file size is :"
                        + FormatFileSize(file.length()));
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            while ((line = mReader.readLine()) != null) {
                bw.append(line);
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "Log reader(catcher) and bufferwriter has closed. ------------------");
            try {
                if (mReader != null) {
                    mReader.close();
                    mReader = null;
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            instance = null;
        }

    }

    public static String FormatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 判断文件是否大于10M。
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean isFileSizeOutof10M(File file) throws Exception {
        if (file == null) return false;
        return file.length() >= 10485760;
    }

    public static void logSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        android.util.Log.w("system", "New Start $$$$$$$$$$$$$$###########   " + time + "############$$$$$$$$$$$$$$$");
        android.util.Log.w("system", "android.os.Build.BOARD:" + android.os.Build.BOARD);
        android.util.Log.w("system", "android.os.Build.DEVICE:" + android.os.Build.DEVICE);
        android.util.Log.w("system", "android.os.Build.MANUFACTURER:"
                + android.os.Build.MANUFACTURER);
        android.util.Log.w("system", "android.os.Build.MODEL:" + android.os.Build.MODEL);
        android.util.Log.w("system", "android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
        android.util.Log.w("system", "android.os.Build.VERSION.CODENAME:"
                + android.os.Build.VERSION.CODENAME);
        android.util.Log.w("system", "android.os.Build.VERSION.RELEASE:"
                + android.os.Build.VERSION.RELEASE);
        //android.util.Log.w("system", "android.os.Build.VERSION.SDK:"
        //        + android.os.Build.VERSION.SDK);
    }
}
