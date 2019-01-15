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
	
	
//	/**
//	 * 生成Bean描述的工具方法
//	 * @param name Bean的Class实例
//	 * @param aliases Bean的别名
//	 * @param fields Bean的字段
//	 * @param methods Bean的函数
//	 * @param isSingleton 单例标记
//	 * @param isPrototype 原型标记
//	 * @param isAbstract 抽象标记
//	 * @return Bean描述实例
//	 */
//	public static BeanDescription generateBeanDescription(Class<?> beanClass, String[] aliases,
//			FieldDescription[] fields, MethodDescription[] methods, boolean isSingleton, boolean isPrototype,
//			boolean isAbstract) {
//		return new BeanDescription(beanClass, aliases, fields, methods, isSingleton, isPrototype, isAbstract);
//	}

	@Override
	public BeanDescription generate(Class<?> beanClass) {
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
