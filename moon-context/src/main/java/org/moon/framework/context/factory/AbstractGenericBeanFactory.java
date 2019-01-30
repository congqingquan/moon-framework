package org.moon.framework.context.factory;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.register.BeanDescriptionRegisterCenter;
import org.moon.framework.beans.register.GenericBeanDescriptionRegistrationCenter;
import org.moon.framework.beans.scanner.BeansDescriptionScanner;

import java.util.List;

/**
 * Created by 明月 on 2019-01-25 / 18:07
 *
 * @email: 1814031271@qq.com
 * @Description: 通用Bean工厂的抽象层
 */
public abstract class AbstractGenericBeanFactory implements BeanFactory {

    /**
     * Bean描述注册中心
     */
    protected final BeanDescriptionRegisterCenter beanDescriptionRegistrationCenter = new GenericBeanDescriptionRegistrationCenter();

    /**
     * Bean扫描器
     */
    protected final BeansDescriptionScanner beanDescriptionScanner = new BeansDescriptionScanner();

    protected void regiest(Class<?> startupClass) {
        // 1. 扫描
        List<BeanDescription> beanDescriptions = beanDescriptionScanner.scan(startupClass.getName());

        // 2. 注册
        beanDescriptions.forEach((beanDescription) -> {
            beanDescriptionRegistrationCenter.register(beanDescription.getBeanName(), beanDescription);
        });
    }

    @Override
    public Object getBean(String beanName) {

        return null;
    }
}