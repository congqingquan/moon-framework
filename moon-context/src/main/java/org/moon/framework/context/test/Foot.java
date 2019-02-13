package org.moon.framework.context.test;

import org.moon.framework.beans.annotation.Service;
import org.moon.framework.beans.annotation.functional.Alias;
import org.moon.framework.beans.annotation.functional.Inject;
import org.moon.framework.beans.annotation.functional.LazyLoad;

/**
 * Created by 明月 on 2019-02-12 / 19:29
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
//@Scope(scope = ScopeSelector.PROTOTYPE)
@LazyLoad
@Alias(aliases = {"f11"})
@Service("sf")
public class Foot {

    public Foot() {
        System.out.println("Foot constructor method");
    }

    @Inject
    Toe toe;

    public Toe getToe() {
        return toe;
    }
}
