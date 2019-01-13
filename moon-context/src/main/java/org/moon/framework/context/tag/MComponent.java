package org.moon.framework.context.tag;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 明月   on 2019-01-13 / 15:59
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 组件标记注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MComponent {
	/**
	 * 组件名称
	 */
	String value() default "";

	/**
	 * 组件单或多个别名
	 */
	String[] alias() default {};

	/**
	 * 单例标记,默认非单实例
	 */
	boolean isSingleton() default false;

	/**
	 * 抽象标记,默认非抽象
	 */
	boolean isAbstract() default false;
}