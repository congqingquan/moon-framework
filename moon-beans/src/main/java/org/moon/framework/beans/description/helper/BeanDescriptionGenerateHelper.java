package org.moon.framework.beans.description.helper;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.generate.BeanDescriptionGenerate;

/**
 * Created by 明月   on 2019-01-14 / 22:35
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 实例化BeanDescription的工具类
 */
public class BeanDescriptionGenerateHelper implements BeanDescriptionGenerate {

	private static BeanDescriptionGenerateHelper beanDescriptionGenerateHelper = new BeanDescriptionGenerateHelper();

	private BeanDescriptionGenerateHelper() {
	}

	public static BeanDescriptionGenerateHelper get() {
		return beanDescriptionGenerateHelper;
	}
	
	@Override
	public BeanDescription generate(Class<?> beanClassParam) {
		
		Class<?> beanClass = beanClassParam;
		String beanClassName = beanClass.getName();
		
		
		
		
		
//		return new GenericBeanDescription(beanClass, beanClassName, aliases, fields, methods, isSingleton, isPrototype, isLazyInit, initMethodName, destroyMethodName)
		return null;
	}
}
