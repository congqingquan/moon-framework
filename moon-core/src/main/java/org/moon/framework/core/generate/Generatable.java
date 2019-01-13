package org.moon.framework.core.generate;

/**
 * Created by 明月   on 2019-01-13 / 19:05
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 可生成的
 */
public interface Generatable<T> {
	public default T generate() {
		// TODO Do nothing
		return null;
	};
}
