package org.moon.framework.context.factory;

import org.moon.framework.beans.register.BeanDescriptionRegisterCenter;
import org.moon.framework.beans.register.GenericBeanDescriptionRegistrationCenter;
import org.moon.framework.beans.scanner.BeansDescriptionScanner;

/**
 * Created by 明月 on 2019-01-25 / 18:06
 *
 * @email: 1814031271@qq.com
 * @Description: 通用的Bean工厂
 */
public class MoonGenericBeanFactory extends AbstractGenericBeanFactory {

    /**
     * Bean描述注册中心
     */
    private BeanDescriptionRegisterCenter genericBeanDescriptionRegistrationCenter = new GenericBeanDescriptionRegistrationCenter();

    /**
     * Bean扫描器
     */
    private final BeansDescriptionScanner beansScanner = new BeansDescriptionScanner();

    public MoonGenericBeanFactory(Class<?> startupClass) {

        // 将beansScanner.scan(startupClass)的结果注册到genericBeanDescriptionRegistrationCenter中

    }
}