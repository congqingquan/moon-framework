package org.moon.framework.beans.description;

import java.lang.reflect.Method;

import org.moon.framework.beans.description.basic.AbstractBeanDescription;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.helper.BeanDescriptionGenerateHelper;

/**
 * Created by 明月   on 2019-01-13 / 16:23
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 通用的BeanDescription描述实体
 */
public class GenericBeanDescription extends AbstractBeanDescription {

	private static BeanDescriptionGenerateHelper beanDescriptionGenerateHelper = BeanDescriptionGenerateHelper.get();

	public static BeanDescription generate(Class<?> beanClass) {
		return beanDescriptionGenerateHelper.generate(beanClass);
	}

	/**
	 * 生成Bean描述的构造方法
	 * @param beanClass Bean的Class实例
	 * @param beanName Bean的名称
	 * @param aliases Bean的别名
	 * @param fields Bean的字段
	 * @param methods Bean的函数
	 * @param isSingleton 单例标记
	 * @param isPrototype 原型标记
	 * @param isLazyInit 懒加载标记
	 * @param initMethods 初始化函数Method实例
	 * @param destroyMethods 销毁函数Method实例
	 * @return Bean描述实例
	 */
	public GenericBeanDescription(Class<?> beanClass, String beanName, String[] aliases, FieldDescription[] fields,
			MethodDescription[] methods, boolean isSingleton, boolean isPrototype, boolean isLazyInit,
			Method[] initMethods, Method[] destroyMethods) {
		super(beanClass, beanName, aliases, fields, methods, isSingleton, isPrototype, isLazyInit, initMethods,
				destroyMethods);
	}
}