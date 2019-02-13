package org.moon.framework.beans.factory;

/**
 * Created by 明月 on 2019-01-13 / 16:45
 *
 * @email: 1814031271@qq.com
 * @Description: Bean工厂接口
 */
public interface BeanFactory {
    Object getBean(String beanName);
    <T> T getBean(Class<T> beanClass);
}
