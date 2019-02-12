package org.moon.framework.beans.container;

/**
 * Created by 明月 on 2019-02-12 / 13:48
 *
 * @email: 1814031271@qq.com
 * @Description: 与BeanName绑定
 */
public interface BeanNameBind {

    default void bind(Object bindVal, String beanName) {
        // TODO -> Do nothing
    }

    default void unbind(String beanName) {
        // TODO -> Do nothing
    }
}
