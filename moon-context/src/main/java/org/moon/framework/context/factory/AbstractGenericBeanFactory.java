package org.moon.framework.context.factory;

import org.moon.framework.beans.annotation.functional.Inject;
import org.moon.framework.beans.container.AliasesContainer;
import org.moon.framework.beans.container.AliasesMapping;
import org.moon.framework.beans.container.TypeContainer;
import org.moon.framework.beans.container.TypeMapping;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.exception.BeanAliasException;
import org.moon.framework.beans.exception.InjectBeanException;
import org.moon.framework.beans.exception.NoUniqueBeanException;
import org.moon.framework.beans.register.BeanDescriptionRegisterCenter;
import org.moon.framework.beans.register.GenericBeanDescriptionRegistrationCenter;
import org.moon.framework.beans.scanner.BeansDescriptionScanner;
import org.moon.framework.context.cache.SingletonBeanCacheContainer;
import org.moon.framework.core.utils.ArrayUtils;
import org.moon.framework.core.utils.ReflectionUtils;
import org.moon.framework.core.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
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
    protected final AliasesContainer aliasesMapping = new AliasesMapping();

    /**
     * 别名 / BeanName的记录容器
     */
    protected final TypeContainer typeMapping = new TypeMapping();

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
        for (Iterator<BeanDescription> iterator = beanDescriptions.iterator(); iterator.hasNext(); ) {
            BeanDescription beanDescription = iterator.next();
            // 2.1 以BeanName为key注册
            beanDescriptionRegistrationCenter.register(beanDescription.getBeanName(), beanDescription);
            // 2.2 校验是否含有重复别名且对别名进行维护(aliases: beanName)
            String[] beanDescriptionAliases = beanDescription.getAliases();
            if(null != beanDescriptionAliases) {
                String beanName = beanDescription.getBeanName();
                for (int i = 0; i < beanDescriptionAliases.length; i++) {
                    if (!checkAliases.add(beanDescriptionAliases[i])) {
                        throw new BeanAliasException("别名重复: " + beanDescriptionAliases[i]);
                    }
                    aliasesMapping.bind(beanDescriptionAliases[i], beanName);
                }
            }
            // 2.3 获取Bean的Class与父级类的Class以及实现接口的Class(包含父类实现的)并与BeanName绑定
            List<Class> typeClasses = ReflectionUtils.getSuperClasses(beanDescription.getBeanClass());
            typeClasses.addAll(ReflectionUtils.getInterfaces(beanDescription.getBeanClass()));
            typeClasses.add(beanDescription.getBeanClass());

            for (Iterator<Class> classIterator = typeClasses.iterator(); classIterator.hasNext(); ) {
                Class typeClass = classIterator.next();
                String bindBeanName = typeMapping.getBeanNameByType(typeClass);

                String className = StringUtils.getClassName(typeClass);
                if (StringUtils.isNotBlank(bindBeanName) && bindBeanName.split("-").length > 1) {
                    typeMapping.bind(typeClass, bindBeanName + "-" + className);
                } else {
                    typeMapping.bind(typeClass, className);
                }
            }
        }
    }

    /**
     * 实例化Bean通过BeanDescription
     *
     * @param beanDescription Bean的描述信息
     */
    protected Object instantiateBean(BeanDescription beanDescription) {
        // 1. 实例化Bean
        Object newInstance = singletonBeanCacheContainer.get(beanDescription.getBeanName());
        if(null == newInstance) {
            newInstance = ReflectionUtils.newInstance(beanDescription.getBeanClass().getName());
        }

        // 2. 获取Bean的需要注入的成员属性
        Field[] fieldByAnnotation = ReflectionUtils.getFieldByAnnotation(beanDescription.getBeanClass(), Inject.class);
        if (null != fieldByAnnotation) {
            // 3. 注入扫描到的成员属性
            for (int i = 0; i < fieldByAnnotation.length; i++) {
                Field field = fieldByAnnotation[i];
                // 3.1 获取配置的BeanName
                String beanName = field.getDeclaredAnnotation(Inject.class).value();
                Object getBean = null;
                // 3.1.1 如果未配置则通过Type进行适配
                if (StringUtils.isBlank(beanName)) {
                    getBean = getBean(field.getType());
                    if (null != getBean) {
                        beanName = typeMapping.getBeanNameByType(field.getType());
                    }
                }
                // 3.1.2 如果配置则通过配置的BeanName进行适配
                else {
                    String beanNameByAlias = aliasesMapping.getBeanNameByAlias(beanName);
                    if (StringUtils.isNotBlank(beanNameByAlias)) {
                        beanName = beanNameByAlias;
                    }
                    getBean = getBean(beanName);
                }
                // 3.2 适配成功后需要对适配的Bean也标记了注入注解的成员也进行适配(递归直至当前的适配Bean不含有标记了注入注解的成员)
                if (null != getBean) {
                    try {
                        field.set(newInstance, getBean);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                // 3.3 适配失败则说明适配属性的源类没有标记为组件,抛出注入失败异常
                else {
                    String beanNameInAnnotation = field.getDeclaredAnnotation(Inject.class).value();
                    if (StringUtils.isNotBlank(beanNameInAnnotation)) {
                        throw new InjectBeanException("注入Bean失败! 未找到Name为: " + beanNameInAnnotation + " 的Bean!");
                    } else {
                        throw new InjectBeanException("注入Bean失败! 未找到Type为: " + field.getType() + " 的Bean!");
                    }
                }
            }
        }

        // 3. 执行初始化函数
        Method[] initMethods = beanDescription.getInitMethods();
        if(null != initMethods) {
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
            String getBeanNameByAlias = aliasesMapping.getBeanNameByAlias(beanName);
            // 1.2 根据别名获取BeanName,如果未获取到则说明真的没有注册到上下文中,如果获取到则说名已经在上下文中注册
            if (null == getBeanNameByAlias) {
                // 1.2.1 未注册则直接返回null
                return null;
            } else {
                // 1.2.2 beanName为参数为别名,则通过aliasesMapping获取对应的beanName并重新赋值
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

    @Override
    public <T> T getBean(Class<T> beanClass) {
        // 1. 根据BeanClass获取Bean的Name
        String beanNameByType = typeMapping.getBeanNameByType(beanClass);

        // 2. 若未注册TypeMapping则返回null
        if (StringUtils.isBlank(beanNameByType)) {
            return null;
        }

        // 3. 若获取到BeanName通过Type
        String[] names = beanNameByType.split("-");
        if (names.length > 1) {
            throw new NoUniqueBeanException("无法获取Bean, 因为" + beanClass.getName() + "存有多个实现: " + ArrayUtils.toString(names));
        }

        // 4. 提取出beanName并根据其进行获取后返回
        return (T) getBean(beanNameByType);
    }
}