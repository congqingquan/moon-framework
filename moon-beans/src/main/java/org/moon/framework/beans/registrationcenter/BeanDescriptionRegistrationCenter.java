package org.moon.framework.beans.registrationcenter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.moon.framework.beans.description.BeanDescription;
import org.moon.framework.beans.exception.BeanNameRepetitionException;

/**
 * Created by 明月   on 2019-01-13 / 16:59
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean信息描述实体的集合
 */
public class BeanDescriptionRegistrationCenter {

	/**
	 * Bean描述统一管理容器
	 */
	private final Map<String, BeanDescription> BEANDESCRIPTION_REGISTRATION_CENTER = new ConcurrentHashMap<>();

	/**
	 * 注册Bean描述
	 * @param beanName bean的名称
	 * @param beanDescription bean的描述实例
	 */
	public void register(String beanName, BeanDescription beanDescription) {
		if (null != BEANDESCRIPTION_REGISTRATION_CENTER.put(beanName, beanDescription))
			throw new BeanNameRepetitionException("BeanName不可重复!!!");
	}

	/**
	 * 注销Bean描述
	 * @param beanName bean的名称
	 */
	public void remove(String beanName) {
		BEANDESCRIPTION_REGISTRATION_CENTER.remove(beanName);
	}
}