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
	 * 生成Bean的信息描述实体
	 * @param beanClass bean的Class
	 * @return
	 */
	BeanDescription generate(Class<?> beanClass);
}
