package org.moon.framework.beans.description;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by 明月   on 2019-01-13 / 18:16
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean Method Info
 */
public class MethodDescription {
	private String methodName;
	private Parameter[] params;
	private String modifer;
	private Class<?> retValType;
	private Method methodInstance;

	public MethodDescription(String methodName,  Parameter[] params, String modifer, Class<?> retValType,
			Method methodInstance) {
		super();
		this.methodName = methodName;
		this.params = params;
		this.modifer = modifer;
		this.retValType = retValType;
		this.methodInstance = methodInstance;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Parameter[] getParams() {
		return params;
	}

	public void setParams(Parameter[] params) {
		this.params = params;
	}

	public String getModifer() {
		return modifer;
	}

	public void setModifer(String modifer) {
		this.modifer = modifer;
	}

	public Class<?> getRetValType() {
		return retValType;
	}

	public void setRetValType(Class<?> retValType) {
		this.retValType = retValType;
	}

	public Method getMethodInstance() {
		return methodInstance;
	}

	public void setMethodInstance(Method methodInstance) {
		this.methodInstance = methodInstance;
	}
}