package com.litesuits.common.utils;

import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * 类工具
 * 
 * @author mty
 * @date 2013-6-10下午8:00:46
 */
public class ClassUtil {

	/**
	 * 判断类是否是基础数据类型
	 * 目前支持11种
	 *
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseDataType(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.equals(String.class) || clazz.equals(Boolean.class)
				|| clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Float.class)
				|| clazz.equals(Double.class) || clazz.equals(Byte.class) || clazz.equals(Character.class)
				|| clazz.equals(Short.class) || clazz.equals(Date.class) || clazz.equals(byte[].class)
				|| clazz.equals(Byte[].class);
	}

	/**
	 * 根据类获取对象
	 * 
	 * @param claxx
	 * @return
	 * @throws Exception
	 */
	public static <T> T newInstance(Class<T> claxx) throws Exception {
		Constructor<T> con = claxx.getDeclaredConstructor();
		con.setAccessible(true);
		return con.newInstance();
	}

}
