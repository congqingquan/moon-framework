package org.moon.framework.beans.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 明月 on 2019-01-25 / 18:16
 *
 * @email: 1814031271@qq.com
 * @Description: 单例Bean缓存中心
 */
public class SingletonBeanCacheContainer implements SingletonBeanContainer {

    /**
     * 所有单例Bean统一管理容器
     */
    private final Map<String, Object> SINGLETONBEAN_CACHECONTAINER = new ConcurrentHashMap<>();

    @Override
    public void put(String beanName, Object obj) {
        SINGLETONBEAN_CACHECONTAINER.put(beanName, obj);
    }

    @Override
    public Object get(String beanName) {
        return SINGLETONBEAN_CACHECONTAINER.get(beanName);
    }

    @Override
    public void clear() {
        SINGLETONBEAN_CACHECONTAINER.clear();
    }

    @Override
    public Map<String, Object> getCacheContainer() {
        return SINGLETONBEAN_CACHECONTAINER;
    }
}