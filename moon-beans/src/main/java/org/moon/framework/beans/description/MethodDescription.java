package org.moon.framework.beans.description;

import java.lang.reflect.Method;

import org.moon.framework.beans.description.generate.MethodDescriptionGenerate;
import org.moon.framework.beans.description.helper.MethodDescriptionGenerateHelper;

/**
 * Created by 明月   on 2019-01-13 / 18:16
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean Method Info
 */
public class MethodDescription implements MethodDescriptionGenerate {
	private String methodName;
	private Class<?>[] params;
	private String modifer;
	private Class<?> retValType;
	private Method methodInstance;
	private MethodDescriptionGenerateHelper methodDescriptionGenerateHelper = MethodDescriptionGenerateHelper.get();

	public MethodDescription(String methodName, Class<?>[] params, String modifer, Class<?> retValType,
			Method methodInstance) {
		super();
		this.methodName = methodName;
		this.params = params;
		this.modifer = modifer;
		this.retValType = retValType;
		this.methodInstance = methodInstance;
	}

	@Override
	public MethodDescription generate(String methodName, Class<?>[] params, String modifer, Class<?> retValType,
			Method methodInstance) {
		return MethodDescriptionGenerate.generateMethodDescription(methodName, params, modifer, retValType,
				methodInstance);
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParams() {
		return params;
	}

	public void setParams(Class<?>[] params) {
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