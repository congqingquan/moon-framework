package org.moon.framework.beans.container;

import org.moon.framework.beans.description.basic.BeanDescription;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 明月 on 2019-01-29 / 21:05
 *
 * @email: 1814031271@qq.com
 * @Description: Bean描述容器的抽象层
 */
public abstract class AbstractBeansDescriptionContainer implements BeansDescriptionContainer {

    /**
     * 所有Bean描述统一管理容器
     */
    protected final Map<String, BeanDescription> BEANDESCRIPTION_REGISTRATION_CENTER = new ConcurrentHashMap<>();

    @Override
    public void putBeanDescription (String beanName, BeanDescription beanDescription) {
        BEANDESCRIPTION_REGISTRATION_CENTER.put(beanName, beanDescription);
    }

    @Override
    public Object getBeanDescription(String beanName) {
        return BEANDESCRIPTION_REGISTRATION_CENTER.get(beanName);
    }

    @Override
    public void removeBeanDescription(String beanName) {
        BEANDESCRIPTION_REGISTRATION_CENTER.remove(beanName);
    }

    @Override
    public Map<String, BeanDescription> getContainer() {
        return BEANDESCRIPTION_REGISTRATION_CENTER;
    }
}