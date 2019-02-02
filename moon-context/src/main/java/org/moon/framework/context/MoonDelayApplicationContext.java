package org.moon.framework.context;

import org.moon.framework.context.factory.AbstractGenericBeanFactory;

/**
 * Created by 明月 on 2019-01-30 / 16:31
 *
 * @email: 1814031271@qq.com
 * @Description: 上下文环境(即时加载)
 */
public class MoonDelayApplicationContext extends AbstractGenericBeanFactory {

    public MoonDelayApplicationContext(Class<?> startupClass) {
        register(startupClass);
    }
}