package org.moon.framework.context.factory;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.exception.BeanAliasException;
import org.moon.framework.beans.register.BeanDescriptionRegisterCenter;
import org.moon.framework.beans.register.GenericBeanDescriptionRegistrationCenter;
import org.moon.framework.beans.scanner.BeansDescriptionScanner;
import org.moon.framework.context.cache.SingletonBeanCacheContainer;
import org.moon.framework.core.utils.ArrayUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 明月 on 2019-01-25 / 18:07
 *
 * @email: 1814031271@qq.com
 * @Description: 通用Bean工厂的抽象层
 */
public abstract class AbstractGenericBeanFactory implements BeanFactory {

    /**
     * 单例Bean的缓存容器
     */
    protected final SingletonBeanCacheContainer singletonBeanCacheContainer = new SingletonBeanCacheContainer();

    /**
     * Bean描述注册中心
     */
    protected final BeanDescriptionRegisterCenter beanDescriptionRegistrationCenter = new GenericBeanDescriptionRegistrationCenter();

    /**
     * Bean扫描器
     */
    protected final BeansDescriptionScanner beanDescriptionScanner = new BeansDescriptionScanner();

    /**
     * 注册Bean描述到注册中心
     *
     * @param startupClass 启动根类
     */
    protected void register(Class<?> startupClass) {
        // 1. 扫描
        List<BeanDescription> beanDescriptions = beanDescriptionScanner.scan(startupClass.getName());
        // 2. 注册
        final Set<String> aliases = new HashSet<>();
        beanDescriptions.forEach((beanDescription) -> {
            // 以BeanName为key注册
            beanDescriptionRegistrationCenter.register(beanDescription.getBeanName(), beanDescription);
            // 校验是否含有重复别名且以别名为key注册
            String[] beanDescriptionAliases = beanDescription.getAliases();
            for (int i = 0; i < beanDescriptionAliases.length; i++) {
                if (!aliases.add(beanDescriptionAliases[i])) {
                    throw new BeanAliasException("别名重复: " + beanDescriptionAliases[i]);
                }
                beanDescriptionRegistrationCenter.register(beanDescriptionAliases[i], beanDescription);
            }
        });
    }

    /**
     * 实例化Bean通过BeanDescription
     *
     * @param beanDescription Bean的描述信息
     */
    protected Object instantiateBean(BeanDescription beanDescription) {

        return null;
    }

    /**
     * 获取单例Bean
     * @param beanName bean的名称或别名
     * @return
     */
    private Object getSingletonBean(String beanName) {
        // 1. 单例Bean缓存容器获取
        Object obj = singletonBeanCacheContainer.get(beanName);
        // 2. 没有获取到则说明不是单例Bean，则去获取BeanDescription
        if (null == obj) {
            // 3. 获取BeanDescription
            BeanDescription beanDescription = beanDescriptionRegistrationCenter.get(beanName);
            // 3.1 获取到了BeanDescription，类型为Singleton则需要实例化完毕后载入单例Bean缓存容器内
            if (beanDescription.isSingleton()) {
                // key为BeanName的形式存储
                Object singletonBean = instantiateBean(beanDescription);
                singletonBeanCacheContainer.put(beanDescription.getBeanName(), singletonBean);
                // key为别名的形式存储
                String[] aliases = beanDescription.getAliases();
                if(ArrayUtils.isNotEmpty(aliases)) {
                    for (int i = 0; i < aliases.length; i++) {
                        singletonBeanCacheContainer.put(aliases[i], singletonBean);
                    }
                }
            }
        }
        // 1.1 为单例Bean则直接返回
        return obj;
    }

    /**
     * IOC中获取Bean
     * @param beanName bean的名称或别名
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        // 1. 首先检测是否注册
        BeanDescription beanDescription = beanDescriptionRegistrationCenter.get(beanName);
        if (null == beanDescription) {
            return null;
        }

        // 2. 如果已注册则去尝试以其为单例Bean进行获取
        Object singletonBean = getSingletonBean(beanName);
        if (null != singletonBean) {
            return singletonBean;
        }

        // 3. 不为单例Bean则为PrototypeBean
        return instantiateBean(beanDescription);
    }
}