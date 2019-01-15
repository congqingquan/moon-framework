package org.moon.framework.beans.description.generate;

import java.lang.reflect.Method;

import org.moon.framework.beans.description.MethodDescription;
import org.moon.framework.core.generate.Generatable;

/**
 * Created by 明月   on 2019-01-13 / 19:07
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 构建方法描述
 */
public interface MethodDescriptionGenerate extends Generatable<MethodDescription> {
	/**
	 * 生成方法描述的方法
	 * @param methodName 方法名称
	 * @param params 参数
	 * @param modifer 修饰符
	 * @param retValType 返回值类型
	 * @param methodInstance 方法的java.lang.reflect.Method实例
	 * @return 方法描述实例
	 */
	MethodDescription generate(String methodName, Class<?>[] params, String modifer, Class<?> retValType,
			Method methodInstance);

	/**
	 * 生成方法描述的工具方法
	 * @param methodName 方法名称
	 * @param params 参数
	 * @param modifer 修饰符
	 * @param retValType 返回值类型
	 * @param methodInstance 方法的java.lang.reflect.Method实例
	 * @return 方法描述实例
	 */
	static MethodDescription generateMethodDescription(String methodName, Class<?>[] params, String modifer,
			Class<?> retValType, Method methodInstance) {
		return new MethodDescription(methodName, params, modifer, retValType, methodInstance);
	}
}
