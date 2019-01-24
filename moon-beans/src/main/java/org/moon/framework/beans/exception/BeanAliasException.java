package org.moon.framework.beans.exception;

/**
 * Created by 明月   on 2019-01-22 / 22:36
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean别名配置异常
 */
public class BeanAliasException extends RuntimeException {

	private static final long serialVersionUID = -4752531445544383451L;

	public BeanAliasException(String message) {
		super(message);
	}
}
