package org.moon.framework.beans.container;

import org.moon.framework.beans.description.basic.BeanDescription;

import java.util.Map;

/**
 * Created by 明月 on 2019-01-13 / 21:14:30
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean描述容器的抽象接口
 */
public interface BeansDescriptionContainer {

    void putBeanDescription(String beanName, BeanDescription beanDescription);

    BeanDescription getBeanDescription(String beanName);

    void removeBeanDescription(String beanName);

    Map<String, BeanDescription> getContainer();
}