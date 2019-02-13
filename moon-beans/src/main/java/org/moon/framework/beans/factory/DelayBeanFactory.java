package org.moon.framework.beans.factory;

/**
 * Created by 明月 on 2019-02-13 / 16:41
 *
 * @email: 1814031271@qq.com
 * @Description: 延迟加载Bean工厂
 */
public class DelayBeanFactory extends AbstractBeanFactory {

    public DelayBeanFactory(Class<?> startupClass) {
        register(startupClass);
    }
}
