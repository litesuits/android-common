package com.litesuits.common.utils;

import com.litesuits.android.log.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author MaTianyu
 * @date 2015-04-18
 */
public class CpuUtil {
    private static final String TAG = CpuUtil.class.getSimpleName();
    private static final String CPU_INFO_PATH = "/proc/cpuinfo";
    private static final String CPU_FREQ_NULL = "N/A";
    private static final String CMD_CAT = "/system/bin/cat";
    private static final String CPU_FREQ_CUR_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    private static final String CPU_FREQ_MAX_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
    private static final String CPU_FREQ_MIN_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";

    /**
     * 打印 CPU 相关信息
     */
    public static String printCpuInfo() {
        return FileUtil.getFileOutputString(CPU_INFO_PATH);
    }

    /**
     * 获取 处理器 核数
     */
    public static int getProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取CPU名字
     */
    public static String getCpuName() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CPU_INFO_PATH), 8192);
            String line = bufferedReader.readLine();
            bufferedReader.close();
            String[] array = line.split(":\\s+", 2);
            if (array.length > 1) {
                if (Log.isPrint) {
                    Log.i(TAG, array[1]);
                }
                return array[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实时获取CPU当前频率
     */
    public static long getCurrentFreq() {
        try {
            return Long.parseLong(FileUtil.getFileOutputString(CPU_FREQ_CUR_PATH).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取CPU最大频率
     */
    public static long getMaxFreq() {
        try {
            return Long.parseLong(getCMDOutputString(new String[]{CMD_CAT, CPU_FREQ_MAX_PATH}).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取CPU最小频率
     */
    public static long getMinFreq() {
        try {
            return Long.parseLong(getCMDOutputString(new String[]{CMD_CAT, CPU_FREQ_MIN_PATH}).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 读取指定命令的输出
     */
    public static String getCMDOutputString(String[] args) {
        try {
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] re = new byte[64];
            int len;
            while ((len = in.read(re)) != -1) {
                sb.append(new String(re, 0, len));
            }
            in.close();
            process.destroy();
            if (Log.isPrint) {
                Log.i(TAG, "CMD: " + sb.toString());
            }
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
