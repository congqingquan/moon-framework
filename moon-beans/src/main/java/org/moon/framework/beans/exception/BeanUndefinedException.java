package org.moon.framework.beans.exception;

/**
 * Created by 明月 on 2019-02-12 / 17:53
 *
 * @email: 1814031271@qq.com
 * @Description: Bean未定义异常
 */
public class BeanUndefinedException extends RuntimeException{
    public BeanUndefinedException(String message) {
        super(message);
    }
}
