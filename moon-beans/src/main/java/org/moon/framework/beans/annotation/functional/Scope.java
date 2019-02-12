package org.moon.framework.beans.annotation.functional;

import org.moon.framework.core.enums.ScopeSelector;

import java.lang.annotation.*;

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
	ScopeSelector scope();
}
