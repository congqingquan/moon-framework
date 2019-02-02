package org.moon.framework.beans.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 明月 on 2019-02-01 / 19:27
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
public class GenericAliasesContainer implements AliasesContainer {

    protected final Map<String, String> ALIASES_BEANNAME = new ConcurrentHashMap<>();

    @Override
    public Map<String, String> getAliasContainer() {
        return ALIASES_BEANNAME;
    }

    @Override
    public void bind(String alias, String beanName) {
        ALIASES_BEANNAME.put(alias, beanName);
    }

    @Override
    public void unbind(String alias) {
        ALIASES_BEANNAME.remove(alias);
    }

    @Override
    public String getBeanNameByAlias(String alias) {
        return ALIASES_BEANNAME.get(alias);
    }
}