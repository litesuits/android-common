package com.litesuits.common.assist;

/**
 * 计时器
 *
 * @author MaTianyu
 * 2013-12-11下午3:42:28
 */
public class TimeCounter {
	
	private long t;

	/**
	 * 重新开始计时
	 * @return
	 */
	public long go() {
		t = System.currentTimeMillis();
		return t;
	}

	/**
	 * 停止计时
	 * @return
	 */
	public long stop() {
		long time = System.currentTimeMillis() - t;
		return time;
	}
}