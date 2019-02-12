package org.moon.framework.beans.description.helper;

import org.moon.framework.beans.annotation.functional.*;
import org.moon.framework.beans.configuration.BeanDescriptionConfiguration;
import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.beans.description.GenericBeanDescription;
import org.moon.framework.beans.description.MethodDescription;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.generate.BeanDescriptionGenerate;
import org.moon.framework.beans.exception.BeanAliasException;
import org.moon.framework.core.utils.ArrayUtils;
import org.moon.framework.core.utils.ReflectionUtils;
import org.moon.framework.core.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by 明月   on 2019-01-14 / 22:35
 *
 * @email: 1814031271@qq.com
 * @Description: 实例化BeanDescription的工具类
 */
public class BeanDescriptionGenerateHelper implements BeanDescriptionGenerate {

    private FieldDescriptionGenerateHelper fieldDescriptionGenerateHelper = FieldDescriptionGenerateHelper.get();

    private MethodDescriptionGenerateHelper methodDescriptionGenerateHelper = MethodDescriptionGenerateHelper.get();

    private static BeanDescriptionGenerateHelper beanDescriptionGenerateHelper = new BeanDescriptionGenerateHelper();

    private BeanDescriptionGenerateHelper() {
    }

    public static BeanDescriptionGenerateHelper get() {
        return beanDescriptionGenerateHelper;
    }

    @Override
    public BeanDescription generate(Class<?> loadClass) {
        // class实例引用
        Class<?> beanClass = loadClass;
        // class实例的名称
        String beanClassName = StringUtils.getClassName(beanClass);
        // 别名
        Alias aliasAnnotation = beanClass.getDeclaredAnnotation(Alias.class);
        String[] aliases = null;
        if (null != aliasAnnotation) {
            String[] aliasesOnAliasAnnotation = aliasAnnotation.aliases();
            aliases = new String[aliasesOnAliasAnnotation.length];
            for (int i = 0; i < aliasesOnAliasAnnotation.length; i++) {
                String aliasStr = aliasesOnAliasAnnotation[i];
                if (StringUtils.isBlank(aliasStr)) {
                    throw new BeanAliasException("Bean配置的别名格式有误!!!");
                } else if (ArrayUtils.contains(aliases, aliasStr)) {
                    throw new BeanAliasException("Bean配置的别名重复!!!");
                } else {
                    aliases[i] = aliasStr;
                }
            }
        }

        // 字段
        Field[] fields = beanClass.getDeclaredFields();
        FieldDescription[] fieldDescriptions = new FieldDescription[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(Boolean.TRUE);
            fieldDescriptions[i] = fieldDescriptionGenerateHelper.generate(field.getName(),
                    Modifier.toString(field.getModifiers()), field.getType(), field);
        }
        // 方法
        Method[] methods = beanClass.getDeclaredMethods();
        MethodDescription[] methodDescriptions = new MethodDescription[methods.length];
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            method.setAccessible(Boolean.TRUE);
            methodDescriptions[i] = methodDescriptionGenerateHelper.generate(method.getName(), method.getParameters(),
                    Modifier.toString(method.getModifiers()), method.getReturnType(), method);
        }
        // 单例或者非单例(默认单例)
        boolean isSingleton = Boolean.TRUE;
        boolean isPrototype = Boolean.FALSE;
        Scope scope = beanClass.getDeclaredAnnotation(Scope.class);
        if (null != scope && scope.scope().getMode().equals(BeanDescriptionConfiguration.SCOPE_PROTOTYPE)) {
            isSingleton = Boolean.FALSE;
            isPrototype = Boolean.TRUE;
        }

        // 是否为懒加载模式(默认非懒加载)
        boolean isLazyInit = Boolean.FALSE;
        LazyLoad lazyLoad = beanClass.getDeclaredAnnotation(LazyLoad.class);
        if (null != lazyLoad) {
            isLazyInit = lazyLoad.value();
        }

        // Bean初始化时执行的函数
        Method[] initMethods = ReflectionUtils.getMethodByAnnotation(beanClass, InitMethod.class);

        // Bean销毁时执行的函数
        Method[] destroyMethods = ReflectionUtils.getMethodByAnnotation(beanClass, DestroyMethod.class);

        return new GenericBeanDescription(beanClass, beanClassName, aliases, fieldDescriptions, methodDescriptions,
                isSingleton, isPrototype, isLazyInit, initMethods, destroyMethods);
    }
}