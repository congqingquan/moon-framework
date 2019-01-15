package org.moon.framework.beans.description.basic;

import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.beans.description.MethodDescription;

/**
 * Created by 明月   on 2019-01-15 / 20:23
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean信息描述的基础抽象层
 */
public abstract class AbstractBeanDescription implements BeanDescription {
	/**
	 * Bean的Class实例
	 */
	protected Class<?> beanClass;
	/**
	 * Bean的类的全限定名
	 */
	protected String beanClassName;
	/**
	 * 别名
	 */
	protected String[] aliases;
	/**
	 * 字段
	 */
	protected FieldDescription[] fields;
	/**
	 * 方法
	 */
	protected MethodDescription[] methods;
	/**
	 * 单例标记
	 */
	protected boolean isSingleton;
	/**
	 * 原型标记
	 */
	protected boolean isPrototype;
	/**
	 * 懒加载标记
	 */
	protected boolean isLazyInit;

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public boolean isPrototype() {
		return isPrototype;
	}

	public void setPrototype(boolean isPrototype) {
		this.isPrototype = isPrototype;
	}

	public String[] getAliases() {
		return aliases;
	}

	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}

	public FieldDescription[] getFields() {
		return fields;
	}

	public void setFields(FieldDescription[] fields) {
		this.fields = fields;
	}

	public MethodDescription[] getMethods() {
		return methods;
	}

	public void setMethods(MethodDescription[] methods) {
		this.methods = methods;
	}

	public boolean isSingleton() {
		return isSingleton;
	}

	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}

	public boolean isLazyInit() {
		return isLazyInit;
	}

	public void setLazyInit(boolean isLazyInit) {
		this.isLazyInit = isLazyInit;
	}
}