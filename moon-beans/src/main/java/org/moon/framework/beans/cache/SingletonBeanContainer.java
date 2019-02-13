package org.moon.framework.beans.cache;

import java.util.Map;

/**
 * Created by 明月 on 2019-02-13 / 17:04
 *
 * @email: 1814031271@qq.com
 * @Description: 单例Bean容器
 */
public interface SingletonBeanContainer {
    void put(String beanName, Object obj);

    Object get(String beanName);

    void clear();

    Map<String, Object> getCacheContainer();
}
