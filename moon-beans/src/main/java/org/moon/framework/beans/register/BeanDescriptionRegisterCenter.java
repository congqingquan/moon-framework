package org.moon.framework.beans.register;

import org.moon.framework.beans.container.BeansDescriptionContainer;
import org.moon.framework.beans.description.basic.BeanDescription;

/**
 * Created by 明月   on 2019-01-13 / 21:37
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean register center
 */
public interface BeanDescriptionRegisterCenter {
	
	void register(String beanName, BeanDescription beanDescription);

	Object get(String beanName);

	void remove(String beanName);

	BeansDescriptionContainer getBeanDescriptionContainer();
}
