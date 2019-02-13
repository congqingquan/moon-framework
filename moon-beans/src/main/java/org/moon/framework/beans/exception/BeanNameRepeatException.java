package org.moon.framework.beans.exception;

/**
 * Created by 明月   on 2019-01-13 / 19:48
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Bean名称重复异常
 */
public class BeanNameRepeatException extends RuntimeException {

	/**
	 * 随机序列化ID
	 */
	private static final long serialVersionUID = 3963279758173621652L;

	public BeanNameRepeatException(String message) {
		super(message);
	}
}
