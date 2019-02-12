package org.moon.framework.context.test;

import org.moon.framework.beans.annotation.Component;
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
@Component
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
