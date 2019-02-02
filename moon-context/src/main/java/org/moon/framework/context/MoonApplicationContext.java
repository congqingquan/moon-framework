package org.moon.framework.context;

import org.moon.framework.beans.container.BeansDescriptionContainer;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.context.factory.AbstractGenericBeanFactory;
import org.moon.framework.core.utils.ArrayUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by 明月 on 2019-01-25 / 18:06
 *
 * @email: 1814031271@qq.com
 * @Description: 上下文环境(预加载)
 */
public class MoonApplicationContext extends AbstractGenericBeanFactory {

    public MoonApplicationContext(Class<?> startupClass) {
        register(startupClass);
        preload();
    }

    /**
     * 预加载
     */
    private void preload() {
        BeansDescriptionContainer beanDescriptionContainer = super.beanDescriptionRegistrationCenter.getBeanDescriptionContainer();
        Map<String, BeanDescription> container = beanDescriptionContainer.getContainer();
        for (Iterator<BeanDescription> iterator = container.values().iterator(); iterator.hasNext(); ) {
            BeanDescription beanDescription = iterator.next();
            // 非懒加载的Bean
            if(!beanDescription.isLazyInit()) {
                // 0. 首先实例化Bean对象通过BeanDescription
                Object beanObj = instantiateBean(beanDescription);
                // 1. 如果为非懒加载的单例Bean
                if(beanDescription.isSingleton()) {
                    // BeanName注册
                    singletonBeanCacheContainer.put(beanDescription.getBeanName(), beanObj);
                    // 别名注册
                    String[] aliases = beanDescription.getAliases();
                    if(ArrayUtils.isNotEmpty(aliases)) {
                        for (int i = 0; i < aliases.length; i++) {
                            singletonBeanCacheContainer.put(aliases[i], beanObj);
                        }
                    }
                }
            }
        }
    }
}