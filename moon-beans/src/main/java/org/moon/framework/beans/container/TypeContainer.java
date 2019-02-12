package org.moon.framework.beans.container;

import java.util.Map;

/**
 * Created by 明月 on 2019-02-12 / 14:14
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
public interface TypeContainer {

    void bind(Class<?> type, String beanName);

    void unbind(String beanName);

    Map<Class<?>, String> getTypeContainer();

    String getBeanNameByType(Class<?> type);
}