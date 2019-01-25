package org.moon.framework.beans.register;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.exception.BeanNameRepetitionException;
import org.moon.framework.beans.scanner.BeansDescriptionScanner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 明月 on 2019-01-25 / 18:27
 *
 * @email: 1814031271@qq.com
 * @Description: Bean信息描述实体的注册中心抽象层
 */
public class AbstractBeanDescriptionRegistrationCenter implements BeanDescriptionRegisterCenter {

    /**
     * Bean描述统一管理容器
     */
    protected final Map<String, BeanDescription> BEANDESCRIPTION_REGISTRATION_CENTER = new ConcurrentHashMap<>();

    /**
     * 注册
     */
    private void registr() {

    }

    /**
     * 注册Bean描述
     * @param beanName bean的名称
     * @param beanDescription bean的描述实例
     */
    @Override
    public void register(String beanName, BeanDescription beanDescription) {
        if (null != BEANDESCRIPTION_REGISTRATION_CENTER.put(beanName, beanDescription))
            throw new BeanNameRepetitionException("BeanName不可重复!!!");
    }

    /**
     * 注销Bean描述
     * @param beanName bean的名称
     */
    @Override
    public void remove(String beanName) {
        BEANDESCRIPTION_REGISTRATION_CENTER.remove(beanName);
    }

    @Override
    public Object get(String beanName) {
        return null;
    }
}