package org.moon.framework.core.parser;

/**
 * Created by 明月   on 2019-01-22 / 19:54
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 解析器接口
 */
public interface Parser<T> {

	T parse(Class<?> loadClass);
}
