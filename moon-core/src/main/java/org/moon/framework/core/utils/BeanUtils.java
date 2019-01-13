package org.moon.framework.core.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 明月   on 2019-01-13 / 23:20
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 操作JavaBean的工具类
 */
public final class BeanUtils {

    private BeanUtils() {
    }

    /**
     * @param from 源Bean
     * @param to   写入Bean
     * @description JavaBean间的字段值的Copy(默认不使用赋空策略)
     * @date 2018-08-21 / 16:04
     * @author C99
     */
    public static void copy(Object from, Object to) {
        copy(from, to, false);
    }

    /**
     * @param from         源Bean
     * @param to           写入Bean
     * @param isIgnoreNull 赋空策略
     * @description JavaBean间的字段值的Copy(支持赋空策略)
     * @date 2018-08-21 / 11:01
     * @author C99
     */
    @SuppressWarnings("unused")
    @Deprecated
    private static void copy0(Object from, Object to, boolean isIgnoreNull) {
        // 获取源Bean的所有字段描述
        PropertyDescriptor[] fromPropertyDescriptors = getPropertyDescriptors(from.getClass());
        PropertyDescriptor[] toPropertyDescriptors = getPropertyDescriptors(to.getClass());

        Assert.isNull(fromPropertyDescriptors, "From bean cant not be empty");
        Assert.isNull(toPropertyDescriptors, "To bean cant not be empty");

        // 迭代ToBean的字段描述
        ToBean:
        for (int i = 0; i < toPropertyDescriptors.length; i++) {
            PropertyDescriptor toPd = toPropertyDescriptors[i];
            // 迭代FromBean的字段描述
            FromBean:
            for (int j = 0; j < fromPropertyDescriptors.length; j++) {
                PropertyDescriptor fromPd = fromPropertyDescriptors[j];
                if (toPd.getName().equals(fromPd.getName())
                        && toPd.getPropertyType().getName().equals(fromPd.getPropertyType().getName())) {
                    // 将ToBean中的字段根据类型与name进行与FromBean的相同字段进行匹配与值的copy
                    try {
                        // 读取FromBean的字段值
                        Object val = fromPd.getReadMethod().invoke(from);
                        // 读取的FromBean内的字段值为空,即是一个空字段
                        if (null == val) {
                            // 则判断赋空策略
                            if (isIgnoreNull) {
                                // 采用赋空策略就赋值为Null
                                toPd.getWriteMethod().invoke(to, val);
                            } else {
                                // 反之跳出该字段的copy
                                break FromBean;
                            }
                        }
                        // 读取的FromBean内的字段值不为空,直接copy赋值给ToBean
                        else
                            toPd.getWriteMethod().invoke(to, val);
                        continue ToBean;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    /**
     * @param from         源Bean
     * @param to           写入Bean
     * @param isIgnoreNull 赋空策略
     * @description JavaBean间的字段值的Copy(支持赋空策略)
     * @date 2018-08-21 / 11:01
     * @author C99
     */
    public static void copy(Object from, Object to, boolean isIgnoreNull) {

        Assert.isNull(from, "from bean cant not be empty");
        Assert.isNull(to, "to bean cant not be empty");

        // 获取ToBean的所有字段描述
        PropertyDescriptor[] toPropertyDescriptors = getPropertyDescriptors(to.getClass());

        Assert.isNull(toPropertyDescriptors, "To bean cant not be empty");

        // 迭代ToBean的字段描述
        ToBean:
        for (int i = 0; i < toPropertyDescriptors.length; i++) {
            // 获取本次迭代的ToBean中的字段
            PropertyDescriptor toPd = toPropertyDescriptors[i];
            // 获取FromBean内相同的字段(相同条件 -> 数据类型&字段名称)
            PropertyDescriptor fromPd = getPropertyDescriptor(toPd.getName(), toPd.getPropertyType(), from.getClass());

            // 如果FromBean内没有ToBean内的字段,则跳过本次copy
            if (null == fromPd)
                continue ToBean;

            try {
                // 读取FromBean的字段值
                Object val = fromPd.getReadMethod().invoke(from);
                // 读取的FromBean内的字段值为空,即是一个空字段
                if (null == val) {
                    // 则判断赋空策略
                    if (isIgnoreNull) {
                        // 采用赋空策略就赋值为Null
                        toPd.getWriteMethod().invoke(to, val);
                    } else {
                        // 反之跳出本次字段的copy
                        continue ToBean;
                    }
                }
                // 读取的FromBean内的字段值不为空,直接copy赋值给ToBean
                else
                    toPd.getWriteMethod().invoke(to, val);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * @param fieldName
     * @param clazz
     * @param @return
     * @return PropertyDescriptor 若Bean中无参数名称的字段则返回null
     * @throws
     * @description 获取某个字段的字段描述
     * @date 2018-08-21 / 17:01
     * @author C99
     */
    public static PropertyDescriptor getPropertyDescriptor(String fieldName, Class<?> clazz) {

        Assert.isNull(clazz, "Class cannot be empty");
        Assert.isNotEmptyString(fieldName, "field name cannot be empty");

        try {
            return new PropertyDescriptor(fieldName, clazz);
        } catch (IntrospectionException e) {
            return null;
        }
    }

    /**
     * @param typeClass
     * @param fieldName
     * @param clazz
     * @return PropertyDescriptor 若Bean中无参数名称且无参数类型的字段则返回null
     * @throws
     * @description 获取某个字段的字段描述
     * @date 2018-08-21 / 17:12
     * @author C99
     */
    public static PropertyDescriptor getPropertyDescriptor(String fieldName, Class<?> typeClass, Class<?> clazz) {

        Assert.isNull(clazz, "Class cannot be empty");
        Assert.isNull(typeClass, "type class cannot be empty");
        Assert.isNotEmptyString(fieldName, "field name cannot be empty");

        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            if (!declaredField.getType().getName().equals(typeClass.getName()))
                return null;
        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }
        return getPropertyDescriptor(fieldName, clazz);
    }

    /**
     * @param beanClazz
     * @return PropertyDescriptor[] 如果没有声明字段则返回null
     * @throws 不符合JavaBean规则抛出 -> java.lang.IllegalArgumentException: the bean has no set or get method
     * @description 获取类的所有字段描述(包含静态字段)
     * @date 2018-08-21 / 13:14
     * @author C99
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClazz) {
        // 检验参数
        Assert.isNull(beanClazz, "bean class cant be empty !");

        // 获取所有的字段
        Field[] declaredFields = beanClazz.getDeclaredFields();
        // 声明字段描述的容器
        PropertyDescriptor[] propertyDescriptor = null;
        if (null != declaredFields && declaredFields.length > 0) {
            // 初始化字段描述的容器
            propertyDescriptor = new PropertyDescriptor[declaredFields.length];
            // 装载
            for (int i = 0; i < declaredFields.length; i++) {
                try {
                    propertyDescriptor[i] = new PropertyDescriptor(declaredFields[i].getName(), beanClazz);
                } catch (IntrospectionException e) {
                    throw new IllegalArgumentException("the bean has no set or get method");
                }
            }
        }
        return propertyDescriptor;
    }

    /**
     * @return boolean
     * @description 校验Bean不为空(注意:忽略对基本数据类型的校验, 因为默认值的问题)
     * @date 2018-08-22 / 15:37
     * @author C99
     */
    public static boolean isNotEmptyBean(Object bean) {

        Assert.isNull(bean, "java bean cannot be empty");

        // 获取参数Bean的所有字段描述
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        // 校验有没有成员字段
        if (declaredFields.length <= 0)
            return false;
        // 若含有成员字段,检验是否存有值(一个字段有值即认为不为空)
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            // 暴力访问
            field.setAccessible(true);
            // 获取Bean的本次迭代的字段值
            try {
                // 如果字段不为空
                if (field.get(bean) != null)
                    return true;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // 打印异常信息后返回false
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * @return boolean
     * @description 校验Bean不为空(不推荐使用, 如果基本数据类型的值为默认值的值, 一律认为是空字段)
     * @date 2018-08-21 / 17:49
     * @author C99
     */
    @SuppressWarnings("unused")
    @Deprecated
    private static boolean isNotEmptyBean0(Object bean) {

        Assert.isNull(bean, "java bean cannot be empty");

        // 获取参数Bean的所有字段描述
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        // 校验有没有成员字段
        if (declaredFields.length <= 0)
            return false;
        // 若含有成员字段,检验是否存有值(一个字段有值即认为不为空)
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            // 暴力访问
            field.setAccessible(true);
            // 获取Bean的本次迭代的字段值
            try {
                Object object = field.get(bean);
                if (object != null) {
                    if (object instanceof Character) {
                        if ((Character) object == 0)
                            continue;
                    }
                    // 不校验String的非空性质,不为null则就认为不是一个空字段,即使是值为"".
                    // else if (object instanceof String) {
                    // // TODO -> check string
                    // }
                    else if (object.toString().equals("0") || object.toString().equals("false")
                            || object.toString().equals("0.0"))
                        continue;
                    else
                        return true;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // 打印异常信息后返回false
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * @description 将JavaBean实体内的字段组装成Key/Value结构
     * @param bean
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @date 2018/8/22 19:16
     * @author C99
     * @throws
     */
    public static Map<String, Object> convertMap(Object bean) {

        Assert.isNull(bean, "bean can not empty");

        // 结果集
        Map<String, Object> resultMap = new HashMap<>();
        // 获取bean的所有字段
        Field[] declaredFields = bean.getClass().getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            // 获取本次迭代的字段
            Field field = declaredFields[i];
            // 暴力访问
            field.setAccessible(true);
            // 装载到结果集中
            try {
                resultMap.put(field.getName(), field.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }
}