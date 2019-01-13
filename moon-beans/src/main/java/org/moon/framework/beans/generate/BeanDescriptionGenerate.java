package org.moon.framework.beans.generate;

import org.moon.framework.beans.description.BeanDescription;
import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.beans.description.MethodDescription;
import org.moon.framework.core.generate.Generatable;

/**
 * Created by 明月   on 2019-01-13 / 18:38
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 构建Bean描述
 */
public interface BeanDescriptionGenerate extends Generatable<BeanDescription> {

	/**
	 * 生成Bean描述
	 * @param name Bean的名称
	 * @param aliases Bean的别名
	 * @param fields Bean的字段
	 * @param methods Bean的函数
	 * @param isSingleton 单例标记
	 * @param isAbstract 抽象标记
	 * @return Bean描述实例
	 */
	BeanDescription generate(String name, String[] aliases, FieldDescription[] fields, MethodDescription[] methods,
			boolean isSingleton, boolean isAbstract);

	/**
	 * 生成Bean描述的工具方法
	 * @param name Bean的名称
	 * @param aliases Bean的别名
	 * @param fields Bean的字段
	 * @param methods Bean的函数
	 * @param isSingleton 单例标记
	 * @param isAbstract 抽象标记
	 * @return Bean描述实例
	 */
	public static BeanDescription generateBeanDescription(String name, String[] aliases, FieldDescription[] fields,
			MethodDescription[] methods, boolean isSingleton, boolean isAbstract) {
		return new BeanDescription(name, aliases, fields, methods, isSingleton, isAbstract);
	}
}
