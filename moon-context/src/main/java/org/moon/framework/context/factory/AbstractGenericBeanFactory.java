package org.moon.framework.context.factory;

import org.moon.framework.beans.container.AliasesContainer;
import org.moon.framework.beans.container.GenericAliasesContainer;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.exception.BeanAliasException;
import org.moon.framework.beans.register.BeanDescriptionRegisterCenter;
import org.moon.framework.beans.register.GenericBeanDescriptionRegistrationCenter;
import org.moon.framework.beans.scanner.BeansDescriptionScanner;
import org.moon.framework.context.cache.SingletonBeanCacheContainer;
import org.moon.framework.core.utils.ArrayUtils;
import org.moon.framework.core.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * 别名 / BeanName的记录容器
     */
    protected final AliasesContainer aliasesContainer = new GenericAliasesContainer();



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
        final Set<String> checkAliases = new HashSet<>();
        beanDescriptions.forEach((beanDescription) -> {
            // 以BeanName为key注册
            beanDescriptionRegistrationCenter.register(beanDescription.getBeanName(), beanDescription);
            // 校验是否含有重复别名且对别名进行维护(aliases: beanName)
            String[] beanDescriptionAliases = beanDescription.getAliases();
            String beanName = beanDescription.getBeanName();
            for (int i = 0; i < beanDescriptionAliases.length; i++) {
                if (!checkAliases.add(beanDescriptionAliases[i])) {
                    throw new BeanAliasException("别名重复: " + beanDescriptionAliases[i]);
                }
                aliasesContainer.bind(beanDescriptionAliases[i], beanName);
            }
        });
    }

    /**
     * 实例化Bean通过BeanDescription
     *
     * @param beanDescription Bean的描述信息
     */
    protected Object instantiateBean(BeanDescription beanDescription) {

        Object newInstance = ReflectionUtils.newInstance(beanDescription.getBeanClass().getName());

        // 执行初始化函数
        Method[] initMethods = beanDescription.getInitMethods();
        for (int i = 0; i < initMethods.length; i++) {
            try {
                initMethods[i].setAccessible(true);
                initMethods[i].invoke(newInstance, new Object[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return newInstance;
    }

    /**
     * 获取单例Bean
     *
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
                if (ArrayUtils.isNotEmpty(aliases)) {
                    for (int i = 0; i < aliases.length; i++) {
                        singletonBeanCacheContainer.put(aliases[i], singletonBean);
                    }
                }
                return singletonBean;
            }
        }
        // 1.1 为单例Bean则直接返回
        return obj;
    }

    /**
     * IOC中通过BeanName获取Bean
     *
     * @param beanName bean的名称或别名
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        // 1. 首先检测是否注册BeanDescription
        BeanDescription beanDescription = beanDescriptionRegistrationCenter.get(beanName);
        if (null == beanDescription) {
            // 1.1 如果未注册BeanDescription则说明可能是别名
            String getBeanNameByAlias = aliasesContainer.getBeanNameByAlias(beanName);
            // 1.2 根据别名获取BeanName,如果未获取到则说明真的没有注册到上下文中,如果获取到则说名已经在上下文中注册
            if (null == getBeanNameByAlias) {
                // 1.2.1 未注册则直接返回null
                return null;
            } else {
                // 1.2.2 beanName为参数为别名,则通过aliasesContainer获取对应的beanName并重新赋值
                beanName = getBeanNameByAlias;
            }
        }

        // 2. 如果已注册则去尝试以其为单例Bean进行获取
        Object singletonBean = getSingletonBean(beanName);
        if (null != singletonBean) {
            return singletonBean;
        }

        // 3. 不为单例Bean则为PrototypeBean
        // 3.1 如果beanDescription为空则说明函数形参beanName参数为别名值,那么自然获取不到
        //     但是能执行到改行说明根据别名值获取到了BeanName,那么根据重新赋值后的beanName重新获取BeanDescription
        if (null == beanDescription) {
            return instantiateBean(beanDescriptionRegistrationCenter.get(beanName));
        }
        // 3.2 beanName形参值不为别名值,则直接在BeanDescription中心获取BeanDescription
        else {
            return instantiateBean(beanDescription);
        }
    }

//    @Override
//    public Object getBean(Class<?> beanClass) {
//        // 1. 获取注册BeanDescription的容器
//        Map<String, BeanDescription> beanDescriptionContainer = beanDescriptionRegistrationCenter.getBeanDescriptionContainer().getContainer();
//        // 2. 提取出所有BeanName
//        String className = StringUtils.getClassName(beanClass);
//        return getBean(className);
//    }
}