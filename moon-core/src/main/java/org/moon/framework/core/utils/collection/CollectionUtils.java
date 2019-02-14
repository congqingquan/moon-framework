package org.moon.framework.core.utils.collection;

import java.util.Collection;
import java.util.Map;

/**
 * Created by 明月   on 2019-01-16 / 15:56
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 集合工具类
 */
public final class CollectionUtils {

	/**
	 * 校验集合不为空
	 * @param collection 需校验的集合
	 * @return 校验结果
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return null != collection ? collection.isEmpty() : true;
	}

	/**
	 * 校验哈希表不为空
	 * @param collection 需校验的Map
	 * @return 校验结果
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return null != map ? map.isEmpty() : true;
	}
}
