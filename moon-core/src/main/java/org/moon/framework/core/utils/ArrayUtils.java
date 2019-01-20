package org.moon.framework.core.utils;

/**
 * Created by 明月   on 2019-01-16 / 16:02
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 数组工具类
 */
public class ArrayUtils {

	/**
	 * 校验数组不为空
	 * @param array 需要校验的数组
	 * @return 校验结果
	 */
	public static boolean isEmpty(Object[] array) {
		return null == array || array.length == 0;
	}

}
