package org.moon.framework.core.utils.collection;

/**
 * Created by 明月   on 2019-01-16 / 16:02
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 数组工具类
 */
public class ArrayUtils {

	private static final String START_FLAG = "[";

	private static final String END_FLAG = "]";

	private static final String ARRAY_FLAG = "[]";

	private static final String ELEMENT_SEPARATOR = ", ";

	/**
	 * 校验数组为空
	 * @param array 需要校验的数组
	 * @return 校验结果
	 */
	public static boolean isEmpty(Object[] array) {
		return null == array || array.length == 0;
	}

	/**
	 * 校验数组不为空
	 * @param array 需要校验的数组
	 * @return 校验结果
	 */
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

	/**
	 * 校验参数数组是包含有指定数据
	 * @param source 源数组
	 * @param obj 需要查询的数据
	 * @return 是否含有
	 */
	public static boolean contains(Object[] source, Object obj) {
		if (isNotEmpty(source)) {
			for (int i = 0; i < source.length; i++) {
				if (source[i] == obj) {
					return true;
				} else if (obj instanceof String) {
					if (source[i] != null && source[i].equals(obj)) {
						return true;
					} else {
						continue;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 有效数据长度
	 * @param source 源数组
	 * @return 有效数据的长度
	 */
	public static Integer validDataLength(Object[] source) {
		Integer count = 0;
		if (isNotEmpty(source))
			for (int i = 0; i < source.length; i++)
				if (null != source[i])
					++count;
		return count;
	}

	/**
	 * 去除空数据并返回
	 * @param source 源数组
	 */
	public static Object[] removeEmptyElement(Object[] source) {
		Object[] result = null;
		if (isNotEmpty(source)) {
			result = new Object[validDataLength(source)];
			for (int i = 0, j = 0; i < source.length; i++) {
				if (null != source[i]) {
					result[j++] = source[i];
				}
			}
			return result;
		}
		return result;
	}

	/**
	 * 打印数组
	 * @param source 源数组
	 * @return 打印数据
	 */
	public static String toString(Object[] source) {
		if (isEmpty(source)) {
			return ARRAY_FLAG;
		}
		StringBuilder buffer = new StringBuilder(START_FLAG);
		for (int i = 0; i < source.length; i++) {
			if (i == source.length - 1) {
				buffer.append(source[i]);
				buffer.append(END_FLAG);
				break;
			}
			buffer.append(source[i]);
			buffer.append(ELEMENT_SEPARATOR);
		}
		return buffer.toString();
	}
}