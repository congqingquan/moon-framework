package org.moon.framework.context.test;

import org.moon.framework.beans.annotation.Component;
import org.moon.framework.beans.annotation.functional.LazyLoad;

/**
 * Created by 明月 on 2019-02-12 / 21:27
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
@LazyLoad
//@Scope(scope = ScopeSelector.PROTOTYPE)
@Component
public class Toe {
    public Toe() {
        System.out.println("Toe constructor method");
    }
}
