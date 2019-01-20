package org.moon.framework.beans.description.helper;

import java.lang.reflect.Field;

import org.moon.framework.beans.description.FieldDescription;
import org.moon.framework.beans.description.generate.FieldDescriptionGenerate;

/**
 * Created by 明月   on 2019-01-15 / 21:15
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 生成字段信息描述实体的工具类
 */
public class FieldDescriptionGenerateHelper implements FieldDescriptionGenerate {

	private static FieldDescriptionGenerateHelper fieldDescriptionGenerateHelper = new FieldDescriptionGenerateHelper();

	private FieldDescriptionGenerateHelper() {
	}

	public static FieldDescriptionGenerateHelper get() {
		return fieldDescriptionGenerateHelper;
	}

	/**
	 * 生成字段描述的工具方法
	 * @param fieldName 字段名称
	 * @param value 字段的值
	 * @param modifer 修饰符
	 * @param type 字段类型
	 * @param fieldInstance 字段的java.lang.reflect.Field实例
	 * @return 字段描述实例
	 */
	@Override
	public FieldDescription generate(String fieldName, Object value, String modifer, Class<?> type,
			Field fieldInstance) {
		return FieldDescriptionGenerate.generateFieldDescription(fieldName, value, modifer, type, fieldInstance);
	}
}
