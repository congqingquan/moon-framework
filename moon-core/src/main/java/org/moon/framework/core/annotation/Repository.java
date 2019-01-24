package org.moon.framework.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 明月   on 2019-01-22 / 21:35
 *
 * @email: 1814031271@qq.com
 *
 * @Description: moon-framework 持久层接口标记注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Repository {
	String value() default "";
}
