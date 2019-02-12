package org.moon.framework.beans.annotation.functional;

import java.lang.annotation.*;

/**
 * Created by 明月 on 2019-02-12 / 18:49
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
    String value() default "";
}