package org.moon.framework.context.test;

import org.moon.framework.beans.annotation.Component;
import org.moon.framework.beans.annotation.functional.Inject;
import org.moon.framework.beans.annotation.functional.LazyLoad;

/**
 * Created by 明月 on 2019-02-12 / 21:22
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
@LazyLoad
@Component
public class Dylan {

    public Dylan() {
        System.out.println("Dylan constructor method");
    }

    @Inject("sf")
    private Foot foot;

    @Inject
    private Hand hand;

    public Foot getFoot() {
        return foot;
    }

    public Hand getHand() {
        return hand;
    }
}