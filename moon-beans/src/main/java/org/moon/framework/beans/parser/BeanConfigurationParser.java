package org.moon.framework.beans.parser;

import org.moon.framework.beans.description.basic.BeanDescription;

import java.util.List;

/**
 * Created by 明月   on 2019-01-22 / 18:33
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 解析Bean配置类的组件
 */
public class BeanConfigurationParser extends AbstractBeansParser implements ConfigurationParser {

	@Override
	public List<BeanDescription> parseConfiguration(Class<?> loadClass) {
		return null;
	}
}
