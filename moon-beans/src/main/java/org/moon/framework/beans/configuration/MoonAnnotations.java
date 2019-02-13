package org.moon.framework.beans.configuration;

import org.moon.framework.beans.annotation.Component;
import org.moon.framework.beans.annotation.Repository;
import org.moon.framework.beans.annotation.Service;

import java.lang.annotation.Annotation;

/**
 * Created by 明月 on 2019-02-13 / 11:14
 *
 * @email: 1814031271@qq.com
 * @Description: Moon framework 注解
 */
public class MoonAnnotations {
    /**
     * 组件注解集合
     */
    @SuppressWarnings("unchecked")
    public static final Class<? extends Annotation>[] COMPONENT_ANNOTATION_CLASSES = (Class<? extends Annotation>[]) new Class[] {
            Component.class,
            Service.class,
            Repository.class
    };
}
