package org.moon.framework.beans.container;

import java.util.Map;

/**
 * Created by 明月 on 2019-02-01 / 19:19
 *
 * @email: 1814031271@qq.com
 * @Description: 别名容器接口
 */
public interface AliasesContainer extends BeanNameBind {

    void bind(String alias, String beanName);

    void unbind(String beanName);

    Map<String, String> getAliasContainer();

    String getBeanNameByAlias(String alias);
}
