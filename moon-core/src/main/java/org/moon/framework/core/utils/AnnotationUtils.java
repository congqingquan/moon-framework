package org.moon.framework.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by 明月   on 2019-01-22 / 21:48
 *
 * @email: 1814031271@qq.com
 * @Description: 注解工具类
 */
public class AnnotationUtils {

    /**
     * AnnotationHandler中的存储注解数据的成员Map
     */
    private static final String MEMBER_VALUES = "memberValues";

    private AnnotationUtils() {
    }

    /**
     * 校验类上是否标记了指定注解
     *
     * @param sourceClass    校验类
     * @param findAnnotation 检测注解
     * @return 标记结果
     */
    public static boolean has(Class<?> sourceClass, Class<? extends Annotation> findAnnotation) {
        return ArrayUtils.isEmpty(sourceClass.getDeclaredAnnotationsByType(findAnnotation));
    }

    /**
     * 校验类上是否标记了指定一组注解
     *
     * @param sourceClass     校验类
     * @param findAnnotations 检测注解
     * @return 标记结果
     */
    public static boolean has(Class<?> sourceClass, Class<? extends Annotation>[] findAnnotations) {
        for (int i = 0; i < findAnnotations.length; i++) {
            Class<? extends Annotation> findAnnotation = findAnnotations[i];
            if (ArrayUtils.isEmpty(sourceClass.getDeclaredAnnotationsByType(findAnnotation))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取注解代理实例的ValueMap
     *
     * @param annotation 注解实例
     * @return ValueMap
     */
    public static Map<Object, Object> getValueMap(Class<?> clazz, Class<? extends Annotation> annotation) {
        if (null == clazz || null == annotation) {
            return null;
        }
        return getValueMap(clazz.getAnnotation(annotation));
    }

    /**
     * 获取注解代理实例的ValueMap
     *
     * @param annotation 注解实例
     * @return ValueMap
     */
    public static Map<Object, Object> getValueMap(Annotation annotation) {
        try {
            InvocationHandler h = Proxy.getInvocationHandler(annotation);

            Field field = h.getClass().getDeclaredField(MEMBER_VALUES);

            field.setAccessible(Boolean.TRUE);

            return (Map<Object, Object>) field.get(h);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}