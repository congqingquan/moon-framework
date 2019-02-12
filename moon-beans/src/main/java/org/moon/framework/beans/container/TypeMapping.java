package org.moon.framework.beans.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 明月 on 2019-02-12 / 14:02
 *
 * @email: 1814031271@qq.com
 * @Description: Class映射
 */
public class TypeMapping implements TypeContainer {

    protected final Map<Class<?>, String> TYPE_BEANNAME = new ConcurrentHashMap<>();

    @Override
    public Map<Class<?>, String> getTypeContainer() {
        return TYPE_BEANNAME;
    }

    @Override
    public String getBeanNameByType(Class<?> type) {
        return TYPE_BEANNAME.get(type);
    }

    @Override
    public void bind(Class<?> type, String beanName) {
        TYPE_BEANNAME.put(type, beanName);
    }

    @Override
    public void unbind(String beanName) {
        TYPE_BEANNAME.remove(beanName);
    }
}