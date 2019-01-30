package org.moon.framework.context;

import org.moon.framework.beans.container.BeansDescriptionContainer;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.context.cache.SingletonBeanCacheContainer;
import org.moon.framework.context.factory.AbstractGenericBeanFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by 明月 on 2019-01-25 / 18:06
 *
 * @email: 1814031271@qq.com
 * @Description: 上下文环境(启动则加载非懒加载的Bean)
 */
public class MoonApplicationContext extends AbstractGenericBeanFactory {

    /**
     * 单例Bean的缓存容器
     */
    private final SingletonBeanCacheContainer singletonBeanCacheContainer = new SingletonBeanCacheContainer();

    public MoonApplicationContext(Class<?> startupClass) {
        regiest(startupClass);
        init();
    }

    private void init() {
        // 3. 实例化Bean
        BeansDescriptionContainer beanDescriptionContainer = super.beanDescriptionRegistrationCenter.getBeanDescriptionContainer();
        Map<String, BeanDescription> container = beanDescriptionContainer.getContainer();
        for (Iterator<BeanDescription> iterator = container.values().iterator(); iterator.hasNext(); ) {
            BeanDescription beanDescription = iterator.next();
            // 非懒加载的Bean
            if(!beanDescription.isLazyInit()) {
                // 非懒加载的单例Bean
                if(beanDescription.isSingleton()) {
                    
                }
                // 非懒加载的PrototypeBean
                else if(beanDescription.isPrototype()) {

                }
            }
        }
    }
}