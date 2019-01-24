package org.moon.framework.beans.parser;

import java.lang.annotation.Annotation;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.helper.BeanDescriptionGenerateHelper;
import org.moon.framework.core.annotation.Api;
import org.moon.framework.core.annotation.Component;
import org.moon.framework.core.annotation.Repository;
import org.moon.framework.core.annotation.Service;

/**
 * Created by 明月   on 2019-01-22 / 19:49
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean描述信息解析组件
 */
public class BeanDescriptionParser extends AbstractBeansParser {
	/**
	 * Bean描述信息生成组件
	 */
	private BeanDescriptionGenerateHelper beanDescriptionGenerateHelper = BeanDescriptionGenerateHelper.get();

	/**
	 * 组件注解集合
	 */
	@SuppressWarnings("unchecked")
	private Class<? extends Annotation>[] annotationClasses = (Class<? extends Annotation>[]) new Class[] { 
			Api.class,
			Component.class, 
			Service.class, 
			Repository.class 
	};
	
	/**
	 * 解析组件
	 */
	@Override
	public BeanDescription parse(Class<?> loadClass) {
		return loadBeanDescription(loadClass, annotationClasses);
	}

	/**
	 * 校验该Class实例是否实现了参数annotationClass类型的类级别的注解
	 * 
	 * @param clazz
	 *            校验的class实例
	 * @return 校验结果,参数为空返回null
	 */
	private BeanDescription loadBeanDescription(Class<?> clazz, 
			@SuppressWarnings("unchecked") Class<? extends Annotation>... annotationClasses) {
		if (null == clazz || null == annotationClasses || annotationClasses.length <= 0)
			return null;
		// 可装载
		if (isLoadable(clazz, annotationClasses))
			return beanDescriptionGenerateHelper.generate(clazz);
		return null;
	}
}
