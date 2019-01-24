package org.moon.framework.beans.parser;

import java.lang.annotation.Annotation;

import org.moon.framework.core.parser.Parser;

/**
 * Created by 明月   on 2019-01-22 / 21:46
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Beans解析器的抽象层
 */
public abstract class AbstractBeansParser  implements Parser<Object>{

	/**
	 * 解析
	 */
	public abstract Object parse(Class<?> loadClass);

	/**
	 * 校验Class上是否标注了指定注解
	 * @param clazz 需要检验的Class对象
	 * @param annotationClasses 指定注解集合
	 * @return 
	 * @return
	 */
	protected boolean isLoadable(Class<?> clazz, 
			@SuppressWarnings("unchecked") Class<? extends Annotation>... annotationClasses) {
		for (int i = 0; i < annotationClasses.length; i++) {
			if (clazz.getDeclaredAnnotation(annotationClasses[i]) == null)
				continue;
			else
				return true;
		}
		return false;
	}
}
