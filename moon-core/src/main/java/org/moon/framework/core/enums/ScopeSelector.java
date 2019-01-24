package org.moon.framework.core.enums;

/**
 * Created by 明月   on 2019-01-24 / 18:20
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 作用域范围选择器
 */
public enum ScopeSelector {

	SINGLETON("singleton"), PROTOTYPE("prototype");

	private String mode;

	private ScopeSelector(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}