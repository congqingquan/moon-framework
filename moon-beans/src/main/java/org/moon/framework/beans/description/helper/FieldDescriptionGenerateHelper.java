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
	
	
	@Override
	public FieldDescription generate(String fieldName, Object value, String modifer, Class<?> type,
			Field fieldInstance) {

		
		
		return null;
	}
}
