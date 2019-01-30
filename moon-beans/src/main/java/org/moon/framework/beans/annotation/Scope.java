package org.moon.framework.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.moon.framework.core.enums.ScopeSelector;

/**
 * Created by 明月   on 2019-01-16 / 16:23
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 作用域注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Scope {
	ScopeSelector scope() default ScopeSelector.SINGLETON;
}
