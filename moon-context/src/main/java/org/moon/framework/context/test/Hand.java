package org.moon.framework.context.test;

import org.moon.framework.beans.annotation.Component;
import org.moon.framework.beans.annotation.functional.LazyLoad;

/**
 * Created by 明月 on 2019-02-12 / 19:29
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
@LazyLoad
@Component
public class Hand {

    public Hand() {
        System.out.println("Hand constructor method");
    }
}
