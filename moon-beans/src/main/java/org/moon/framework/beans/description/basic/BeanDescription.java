package org.moon.framework.beans.description.basic;

import org.moon.framework.beans.config.BeanDescriptionConfiguration;
import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.beans.description.MethodDescription;

/**
 * Created by 明月   on 2019-01-15 / 18:41
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean信息描述
 */
public interface BeanDescription {

	/**
	 * 单例
	 */
	String SCOPE_SINGLETON = BeanDescriptionConfiguration.SCOPE_SINGLETON;

	/**
	 * 多例
	 */
	String SCOPE_PROTOTYPE = BeanDescriptionConfiguration.SCOPE_PROTOTYPE;

	String getBeanClassName();

	void setBeanClassName(String beanClassName);

	Class<?> getBeanClass();

	void setBeanClass(Class<?> beanClass);

	boolean isPrototype();

	void setPrototype(boolean isPrototype);

	String[] getAliases();

	void setAliases(String[] aliases);

	FieldDescription[] getFields();

	void setFields(FieldDescription[] fields);

	MethodDescription[] getMethods();

	void setMethods(MethodDescription[] methods);

	boolean isSingleton();

	void setSingleton(boolean isSingleton);

	boolean isLazyInit();

	void setLazyInit(boolean isLazyInit);
}