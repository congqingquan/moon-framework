package org.moon.framework.beans.description;

import org.moon.framework.beans.generate.BeanDescriptionGenerate;

/**
 * Created by 明月   on 2019-01-13 / 16:23
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean Info
 */
public class BeanDescription implements BeanDescriptionGenerate {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 别名
	 */
	private String[] aliases;
	/**
	 * 字段
	 */
	private FieldDescription[] fields;
	/**
	 * 方法
	 */
	private MethodDescription[] methods;
	/**
	 * 单例标记
	 */
	private boolean isSingleton;

	/**
	 * 抽象标记
	 */
	private boolean isAbstract;

	public BeanDescription(String name, String[] aliases, FieldDescription[] fields, MethodDescription[] methods,
			boolean isSingleton, boolean isAbstract) {
		super();
		this.name = name;
		this.aliases = aliases;
		this.fields = fields;
		this.methods = methods;
		this.isSingleton = isSingleton;
		this.isAbstract = isAbstract;
	}

	@Override
	public BeanDescription generate(String name, String[] aliases, FieldDescription[] fields,
			MethodDescription[] methods, boolean isSingleton, boolean isAbstract) {
		return BeanDescriptionGenerate.generateBeanDescription(name, aliases, fields, methods, isSingleton, isAbstract);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}
}