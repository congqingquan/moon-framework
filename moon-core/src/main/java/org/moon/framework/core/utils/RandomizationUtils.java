package org.moon.framework.core.utils;

import java.util.Collection;
import java.util.Random;

/**
 * Created by 明月   on 2019-01-13 / 23:21
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Shuffle算法类
 */
public class RandomizationUtils {

	/**
	 * 集合洗牌
	 */
	@SuppressWarnings("unchecked")
	public static <T> void shuffle(Collection<T> collection) {
		if (null != collection && collection.size() > 0) {
			// 结果集
			T[] temp;
			// 洗牌
			shuffle(collection.toArray((temp = (T[]) new Object[collection.size()])));
			// 清除源数据
			collection.clear();
			// 注入结果集
			for (int i = 0; i < temp.length; i++)
				collection.add(temp[i]);
		}
	}

	/**
	 * 数组洗牌
	 */
	public static <T> void shuffle(T[] array) {
		if (null != array && array.length > 0) {
			// 1. 随机实例
			Random random = new Random();
			// 2. 洗牌
			for (int i = array.length - 1; i >= 1; i--)
				// nextInt: 生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n
				swap(array, i, random.nextInt(i));
			// 3. 执行结束
			return;
		}
	}

	/**
	 * 互换i - j索引处的数据.
	 */
	public static <T> void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}