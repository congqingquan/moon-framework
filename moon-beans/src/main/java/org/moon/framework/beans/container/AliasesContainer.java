package org.moon.framework.beans.container;

import java.util.Map;

/**
 * Created by 明月 on 2019-02-01 / 19:19
 *
 * @email: 1814031271@qq.com
 * @Description: 别名容器接口
 */
public interface AliasesContainer {
    Map<String, String> getAliasContainer();

    void bind(String alias, String beanName);

    void unbind(String alias);

    String getBeanNameByAlias(String beanName);
}
