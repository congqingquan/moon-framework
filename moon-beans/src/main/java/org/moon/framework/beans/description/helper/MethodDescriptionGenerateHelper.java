package org.moon.framework.beans.description.helper;

import java.lang.reflect.Method;

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

	@Override
	public MethodDescription generate(String methodName, Class<?>[] params, String modifer, Class<?> retValType,
			Method methodInstance) {
		return null;
	}

}
