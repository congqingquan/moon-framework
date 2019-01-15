package org.moon.framework.beans.description.generate;

import org.moon.framework.beans.description.basic.BeanDescription;
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
	 * @param beanClass Bean的Class实例
	 * @param aliases Bean的别名
	 * @param fields Bean的字段
	 * @param methods Bean的函数
	 * @param isSingleton 单例标记
	 * @param isPrototype 原型标记
	 * @param isAbstract 抽象标记
	 * @return Bean描述实例
	 */
	BeanDescription generate(Class<?> beanClass);
}
