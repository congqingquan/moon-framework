package org.moon.framework.beans.register;

import org.moon.framework.beans.container.BeansDescriptionContainer;
import org.moon.framework.beans.container.GenericBeansDescriptionContainer;
import org.moon.framework.beans.description.basic.BeanDescription;


/**
 * Created by 明月 on 2019-01-25 / 18:27
 *
 * @email: 1814031271@qq.com
 * @Description: Bean信息描述实体的注册中心抽象层
 */
public abstract class AbstractBeanDescriptionRegistrationCenter implements BeanDescriptionRegisterCenter {

    /**
     * 通用的Bean描述容器
     */
    private final BeansDescriptionContainer GENERIC_BEANSDESCRIPTION_CONTAINER = new GenericBeansDescriptionContainer();

    /**
     * 注册Bean描述
     * @param beanName bean的名称
     * @param beanDescription bean的描述实例
     */
    @Override
    public void register(String beanName, BeanDescription beanDescription) {
        GENERIC_BEANSDESCRIPTION_CONTAINER.putBeanDescription(beanName, beanDescription);
    }

    /**
     * 注销Bean描述
     * @param beanName bean的名称
     */
    @Override
    public void remove(String beanName) {
        GENERIC_BEANSDESCRIPTION_CONTAINER.removeBeanDescription(beanName);
    }

    /**
     * 获取Bean描述
     * @param beanName bean的名称
     * @return
     */
    @Override
    public BeanDescription get(String beanName) {
        return GENERIC_BEANSDESCRIPTION_CONTAINER.getBeanDescription(beanName);
    }

    /**
     * 获取存储BeanDescription的容器
     * @return
     */
    @Override
    public BeansDescriptionContainer getBeanDescriptionContainer() {
        return GENERIC_BEANSDESCRIPTION_CONTAINER;
    }
}