package org.moon.framework.beans.description.helper;

import org.moon.framework.beans.annotation.functional.Alias;
import org.moon.framework.beans.annotation.functional.DestroyMethod;
import org.moon.framework.beans.annotation.functional.InitMethod;
import org.moon.framework.beans.annotation.functional.LazyLoad;
import org.moon.framework.beans.annotation.functional.Scope;
import org.moon.framework.beans.configuration.BeanDescriptionConfiguration;
import org.moon.framework.beans.configuration.MoonAnnotations;
import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.beans.description.GenericBeanDescription;
import org.moon.framework.beans.description.MethodDescription;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.generate.BeanDescriptionGenerate;
import org.moon.framework.beans.exception.BeanAliasException;
import org.moon.framework.beans.exception.BeanDefinitionException;
import org.moon.framework.core.utils.reflection.AnnotationUtils;
import org.moon.framework.core.utils.collection.ArrayUtils;
import org.moon.framework.core.utils.reflection.ReflectionUtils;
import org.moon.framework.core.utils.basic.StringUtils;

import java.lang.annotation.Annotation;
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
        // 1. beanClass
        Class<?> beanClass = loadClass;

        // 2. beanName
        String beanName = generateBeanName(beanClass);

        // 3. 别名
        String[] aliases = generateBeanAlias(beanClass);

        // 4. 字段
        FieldDescription[] fieldDescriptions = generateFieldDescription(beanClass);

        // 5. 方法
        MethodDescription[] methodDescriptions = generateMethodDescription(beanClass);

        // 6. 单例或者非单例(默认单例)
        boolean isSingleton = Boolean.TRUE;
        boolean isPrototype = Boolean.FALSE;
        Scope scope = beanClass.getDeclaredAnnotation(Scope.class);
        if (null != scope && scope.scope().getMode().equals(BeanDescriptionConfiguration.SCOPE_PROTOTYPE)) {
            isSingleton = Boolean.FALSE;
            isPrototype = Boolean.TRUE;
        }

        // 7. 是否为懒加载模式(默认非懒加载)
        boolean isLazyInit = Boolean.FALSE;
        LazyLoad lazyLoad = beanClass.getDeclaredAnnotation(LazyLoad.class);
        if (null != lazyLoad) {
            isLazyInit = lazyLoad.value();
        }

        // 8. Bean初始化时执行的函数
        Method[] initMethods = ReflectionUtils.getMethodByAnnotation(beanClass, InitMethod.class);

        // 9. Bean销毁时执行的函数
        Method[] destroyMethods = ReflectionUtils.getMethodByAnnotation(beanClass, DestroyMethod.class);

        return new GenericBeanDescription(beanClass, beanName, aliases, fieldDescriptions, methodDescriptions,
                isSingleton, isPrototype, isLazyInit, initMethods, destroyMethods);
    }

    /**
     * 生成BeanName
     *
     * @param beanClass bean的Class实例
     * @return Bean Name
     */
    private String generateBeanName(Class<?> beanClass) {
        String beanName = null;
        // 1. 获取组件标记注解中配置的BeanName
        Class<? extends Annotation>[] componentAnnotationClasses = MoonAnnotations.COMPONENT_ANNOTATION_CLASSES;
        for (int i = 0, componentAnnotationCount = 0; i < componentAnnotationClasses.length; i++) {
            Class<? extends Annotation> componentAnnotationClass = componentAnnotationClasses[i];
            if (null == beanClass.getAnnotation(componentAnnotationClass)) {
                continue;
            }
            if (++componentAnnotationCount > 1) {
                throw new BeanDefinitionException("Bean定义错误! 勿定义Bean为多种类型的组件!");
            }
            beanName = AnnotationUtils.getValueMap(beanClass.getAnnotation(componentAnnotationClass)).get("value").toString();
        }
        // 2. 如果未配置则类名首字母小写作为BeanName
        if(StringUtils.isBlank(beanName)) {
            beanName = StringUtils.getClassName(beanClass);
        }
        return beanName;
    }

    /**
     * 生成Bean的别名
     *
     * @param beanClass bean的Class实例
     * @return 别名数组
     */
    private String[] generateBeanAlias(Class<?> beanClass) {
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
        return aliases;
    }

    /**
     * 生成Bean的字段描述
     *
     * @param beanClass bean的Class实例
     * @return 字段描述数组
     */
    private FieldDescription[] generateFieldDescription(Class<?> beanClass) {
        Field[] fields = beanClass.getDeclaredFields();
        FieldDescription[] fieldDescriptions = new FieldDescription[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(Boolean.TRUE);
            fieldDescriptions[i] = fieldDescriptionGenerateHelper.generate(field.getName(),
                    Modifier.toString(field.getModifiers()), field.getType(), field);
        }
        return fieldDescriptions;
    }

    /**
     * 生成Bean的方法描述
     *
     * @param beanClass beanClass bean的Class实例
     * @return 方法描述数组
     */
    private MethodDescription[] generateMethodDescription(Class<?> beanClass) {
        Method[] methods = beanClass.getDeclaredMethods();
        MethodDescription[] methodDescriptions = new MethodDescription[methods.length];
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            method.setAccessible(Boolean.TRUE);
            methodDescriptions[i] = methodDescriptionGenerateHelper.generate(method.getName(), method.getParameters(),
                    Modifier.toString(method.getModifiers()), method.getReturnType(), method);
        }
        return methodDescriptions;
    }
}