package org.moon.framework.beans.description.generate;

import java.lang.reflect.Field;

import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.core.generate.Generatable;

/**
 * Created by 明月   on 2019-01-13 / 18:38
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 构建字段描述
 */
public interface FieldDescriptionGenerate extends Generatable<FieldDescription> {

	/**
	 * 生成字段描述的方法
	 * @param fieldName 字段名称
	 * @param value 字段的值
	 * @param modifer 修饰符
	 * @param type 字段类型
	 * @param fieldInstance 字段的java.lang.reflect.Field实例
	 * @return 字段描述实例
	 */
	FieldDescription generate(String fieldName, String modifer, Class<?> type,
			Field fieldInstance);

	/**
	 * 生成字段描述的工具方法
	 * @param fieldName 字段名称
	 * @param value 字段的值
	 * @param modifer 修饰符
	 * @param type 字段类型
	 * @param fieldInstance 字段的java.lang.reflect.Field实例
	 * @return 字段描述实例
	 */
	static FieldDescription generateFieldDescription(String fieldName, String modifer, Class<?> type,
			Field fieldInstance) {
		return new FieldDescription(fieldName, modifer, type, fieldInstance);
	}
}
