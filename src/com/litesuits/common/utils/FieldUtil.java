package com.litesuits.common.utils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 域工具
 * 
 * @author mty
 * @date 2013-6-10下午6:36:29
 */
public class FieldUtil {

	/**
	 * 判断是否序列化
	 * @param f
	 * @return
	 */
	public static boolean isSerializable(Field f) {
		Class<?>[] cls = f.getType().getInterfaces();
		for (Class<?> c : cls) {
			if (Serializable.class == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置域的值
	 * @param f
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Object set(Field f, Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		f.set(obj, value);
		return f.get(obj);
	}

	/**
	 * 获取域的值
	 * @param f
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Object get(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		return f.get(obj);
	}
}
