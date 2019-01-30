package org.moon.framework.beans.scanner;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.parser.BeanConfigurationParser;
import org.moon.framework.beans.parser.BeanDescriptionParser;
import org.moon.framework.beans.parser.ConfigurationParser;
import org.moon.framework.beans.util.ClassPathDissolver;
import org.moon.framework.core.constant.Constant;
import org.moon.framework.core.utils.Assert;
import org.moon.framework.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 明月   on 2019-01-13 / 22:46
 *
 * @email: 1814031271@qq.com
 * @Description: Beans扫描器
 */
public class BeansDescriptionScanner {

    /**
     * Bean描述解析组件
     */
    private final BeanDescriptionParser beanDescriptionParser = new BeanDescriptionParser();

    /**
     * Bean配置解析组件
     */
    private final ConfigurationParser beanConfigurationParser = new BeanConfigurationParser();


    /**
     * 扫描程序启动类下的所有实现了annotationClass参数类型注解的类
     *
     * @param startupClassQualifiedName 扫描路径(启动类的全限定名)
     * @return 若路径无效或路径下无符合的component则返回空的List集合
     */
    public List<BeanDescription> scan(String startupClassQualifiedName) {

        Assert.isEmptyString(startupClassQualifiedName, "组件扫描路径不能为空!!!");

        // 0. 若全限定名格式路径为空,则默认扫描classpath下的所有文件
        if (StringUtils.isBlank(startupClassQualifiedName)) {
            startupClassQualifiedName = Constant.Path.CLASSPATH;
        }

        // 1. 初始化装载BeanDescription的容器
        List<BeanDescription> beanDescriptions = new ArrayList<>();

        // 2. 获取路径下所有Class的全限定名
        List<String> qualifiedNames = ClassPathDissolver.getQualifiedNamesInClassPath(startupClassQualifiedName);

        // 3. 根据这些全限定名获取所有实现了annotationClass参数类型注解的类的BeanDescription
        for (Iterator<String> iterator = qualifiedNames.iterator(); iterator.hasNext(); ) {
            String qualifiedName = iterator.next();
            // 4. 根据全限定名加载Class实例
            Class<?> loadClass = null;
            try {
                loadClass = Class.forName(qualifiedName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // 5. load...
            // 加载配置
            List<BeanDescription> configBeans = beanConfigurationParser.parseConfiguration(loadClass);
            if (null != configBeans) {
                beanDescriptions.addAll(configBeans);
            }

            // 加载BeanDescription
            beanDescriptions.add(beanDescriptionParser.parse(loadClass));
        }
        return beanDescriptions;
    }
}