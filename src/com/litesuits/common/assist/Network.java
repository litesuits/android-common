package com.litesuits.common.assist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.litesuits.android.log.Log;

import java.lang.reflect.Method;

/**
 * 
 * assist us in sensing state of the networks.
 *
 * need  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *
 * 半夜了，研究了一下Android的Network方面，发现网上有些文章理解的是不对的。
 * 以下方法是我研究得出的结论和方法，如有误也感谢指出。
 * 
 * @author MaTianyu
 * 2014-1-8上午 00:33:11
 */
public class Network {
	private static final String TAG = Network.class.getSimpleName();

	public enum NetType {
		None(1),
		Mobile(2),
		Wifi(4),
		Other(8);
		NetType(int value) {
			this.value = value;
		}
		public int value;
	}

	/**
	 * 获取ConnectivityManager
	 */
	public static ConnectivityManager getConnManager(Context context) {
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * 判断网络连接是否有效（此时可传输数据）。
	 * @param context
	 * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
	 */
	public static boolean isConnected(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		return net != null && net.isConnected();
	}

	/**
	 * 判断有无网络正在连接中（查找网络、校验、获取IP等）。
	 * @param context
	 * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
	 */
	public static boolean isConnectedOrConnecting(Context context) {
		NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
		if (nets != null) {
			for (NetworkInfo net : nets) {
				if (net.isConnectedOrConnecting()) { return true; }
			}
		}
		return false;
	}

	public static NetType getConnectedType(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		if (net != null) {
			switch (net.getType()) {
				case ConnectivityManager.TYPE_WIFI :
					return NetType.Wifi;
				case ConnectivityManager.TYPE_MOBILE :
					return NetType.Mobile;
				default :
					return NetType.Other;
			}
		}
		return NetType.None;
	}

	/**
	 * 是否存在有效的WIFI连接
	 */
	public static boolean isWifiConnected(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		return net != null && net.getType() == ConnectivityManager.TYPE_WIFI && net.isConnected();
	}

	/**
	 * 是否存在有效的移动连接
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobileConnected(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		return net != null && net.getType() == ConnectivityManager.TYPE_MOBILE && net.isConnected();
	}

	/**
	 * 检测网络是否为可用状态
	 */
	public static boolean isAvailable(Context context) {
		return isWifiAvailable(context) || (isMobileAvailable(context) && isMobileEnabled(context));
	}

	/**
	 * 判断是否有可用状态的Wifi，以下情况返回false：
	 *  1. 设备wifi开关关掉;
	 *  2. 已经打开飞行模式；
	 *  3. 设备所在区域没有信号覆盖；
	 *  4. 设备在漫游区域，且关闭了网络漫游。
	 *  
	 * @param context
	 * @return boolean wifi为可用状态（不一定成功连接，即Connected）即返回ture
	 */
	public static boolean isWifiAvailable(Context context) {
		NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
		if (nets != null) {
			for (NetworkInfo net : nets) {
				if (net.getType() == ConnectivityManager.TYPE_WIFI) { return net.isAvailable(); }
			}
		}
		return false;
	}

	/**
	 * 判断有无可用状态的移动网络，注意关掉设备移动网络直接不影响此函数。
	 * 也就是即使关掉移动网络，那么移动网络也可能是可用的(彩信等服务)，即返回true。
	 * 以下情况它是不可用的，将返回false：
	 *  1. 设备打开飞行模式；
	 *  2. 设备所在区域没有信号覆盖；
	 *  3. 设备在漫游区域，且关闭了网络漫游。
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobileAvailable(Context context) {
		NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
		if (nets != null) {
			for (NetworkInfo net : nets) {
				if (net.getType() == ConnectivityManager.TYPE_MOBILE) { return net.isAvailable(); }
			}
		}
		return false;
	}

	/**
	 * 设备是否打开移动网络开关
	 * @param context
	 * @return boolean 打开移动网络返回true，反之false
	 */
	public static boolean isMobileEnabled(Context context) {
		try {
			Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
			getMobileDataEnabledMethod.setAccessible(true);
			return (Boolean) getMobileDataEnabledMethod.invoke(getConnManager(context));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 反射失败，默认开启
		return true;
	}

	/**
	 * 打印当前各种网络状态
	 * @param context
	 * @return boolean
	 */
	public static boolean printNetworkInfo(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo in = connectivity.getActiveNetworkInfo();
			Log.i(TAG, "-------------$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-------------");
			Log.i(TAG, "getActiveNetworkInfo: " + in);
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					// if (info[i].getType() == ConnectivityManager.TYPE_WIFI) {
					Log.i(TAG, "NetworkInfo[" + i + "]isAvailable : " + info[i].isAvailable());
					Log.i(TAG, "NetworkInfo[" + i + "]isConnected : " + info[i].isConnected());
					Log.i(TAG, "NetworkInfo[" + i + "]isConnectedOrConnecting : " + info[i].isConnectedOrConnecting());
					Log.i(TAG, "NetworkInfo[" + i + "]: " + info[i]);
					// }
				}
				Log.i(TAG, "\n");
			} else {
				Log.i(TAG, "getAllNetworkInfo is null");
			}
		}
		return false;
	}

}