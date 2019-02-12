package org.moon.framework.beans.annotation.functional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 明月   on 2019-01-22 / 23:40
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean销毁时执行的函数的标记注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DestroyMethod {
}
