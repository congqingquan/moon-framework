package org.moon.framework.beans.exception;

/**
 * Created by 明月 on 2019-2-12 / 11:49
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean别名配置异常
 */
public class BeanParseException extends RuntimeException {

	private static final long serialVersionUID = -4752531445544383451L;

	public BeanParseException(String message) {
		super(message);
	}
}
