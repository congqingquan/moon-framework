package org.moon.framework.beans.parser;

import org.moon.framework.beans.configuration.MoonAnnotations;
import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.helper.BeanDescriptionGenerateHelper;
import org.moon.framework.beans.exception.BeanParseException;
import org.moon.framework.core.parser.Parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

/**
 * Created by 明月   on 2019-01-22 / 21:46
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Beans解析器的抽象层
 */
public abstract class AbstractBeansParser implements Parser<BeanDescription>{

	/**
	 * Bean描述信息生成组件
	 */
	private BeanDescriptionGenerateHelper beanDescriptionGenerateHelper = BeanDescriptionGenerateHelper.get();

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
			if (clazz.getDeclaredAnnotation(annotationClasses[i]) == null) {
				continue;
            }
			else {
			    // 如果在抽象类中标记了组件注解则直接抛出异常
                if(Modifier.isAbstract(clazz.getModifiers())) {
                    throw new BeanParseException("组件不能为Abstract");
                }
                return true;
			}
		}
		return false;
	}

	/**
	 * 解析组件
	 */
	@Override
	public BeanDescription parse(Class<?> loadClass) {
		return loadBeanDescription(loadClass, MoonAnnotations.COMPONENT_ANNOTATION_CLASSES);
	}

	/**
	 * 校验该Class实例是否实现了参数annotationClass类型的类级别的注解
	 *
	 * @param clazz
	 *            校验的class实例
	 * @return 校验结果,参数为空返回null
	 */
	private BeanDescription loadBeanDescription(Class<?> clazz,
												@SuppressWarnings("unchecked") Class<? extends Annotation>... annotationClasses) {
		if (null == clazz || null == annotationClasses || annotationClasses.length <= 0)
			return null;
		// 可装载
		if (isLoadable(clazz, annotationClasses))
			return beanDescriptionGenerateHelper.generate(clazz);
		return null;
	}
}