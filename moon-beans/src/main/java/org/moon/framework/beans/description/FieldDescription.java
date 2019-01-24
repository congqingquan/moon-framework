package org.moon.framework.beans.description;

import java.lang.reflect.Field;

/**
 * Created by 明月   on 2019-01-13 / 18:16
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean Field Info
 */
public class FieldDescription {
	private String fieldName;
	private String modifer;
	private Class<?> type;
	private Field fieldInstance;

	public FieldDescription(String fieldName, String modifer, Class<?> type, Field fieldInstance) {
		super();
		this.fieldName = fieldName;
		this.modifer = modifer;
		this.type = type;
		this.fieldInstance = fieldInstance;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getModifer() {
		return modifer;
	}

	public void setModifer(String modifer) {
		this.modifer = modifer;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Field getFieldInstance() {
		return fieldInstance;
	}

	public void setFieldInstance(Field fieldInstance) {
		this.fieldInstance = fieldInstance;
	}
}