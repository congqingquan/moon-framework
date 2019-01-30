package org.moon.framework.beans.parser;

import org.moon.framework.beans.description.basic.BeanDescription;

import java.util.List;

/**
 * Created by 明月   on 2019-01-22 / 17:10:47
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 解析Bean配置类的组件接口
 */
public interface ConfigurationParser {
    /**
     * 解析配置文件
     */
    List<BeanDescription> parseConfiguration(Class<?> loadClass);
}
