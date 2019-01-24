package org.moon.framework.beans.description.helper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.moon.framework.beans.description.MethodDescription;
import org.moon.framework.beans.description.generate.MethodDescriptionGenerate;

/**
 * Created by 明月   on 2019-01-15 / 21:03
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 生成方法信息描述实体的工具类
 */
public class MethodDescriptionGenerateHelper implements MethodDescriptionGenerate {

	private static MethodDescriptionGenerateHelper methodDescriptionGenerateHelper = new MethodDescriptionGenerateHelper();

	private MethodDescriptionGenerateHelper() {
	}

	public static MethodDescriptionGenerateHelper get() {
		return methodDescriptionGenerateHelper;
	}

	/**
	 * 生成方法描述的工具方法
	 * @param methodName 方法名称
	 * @param params 参数
	 * @param modifer 修饰符
	 * @param retValType 返回值类型
	 * @param methodInstance 方法的java.lang.reflect.Method实例
	 * @return 方法描述实例
	 */
	@Override
	public MethodDescription generate(String methodName, Parameter[] params, String modifer, Class<?> retValType,
			Method methodInstance) {
		return MethodDescriptionGenerate.generateMethodDescription(methodName, params, modifer, retValType,
				methodInstance);
	}
}