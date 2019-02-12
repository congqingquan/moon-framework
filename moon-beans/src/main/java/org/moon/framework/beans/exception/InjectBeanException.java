package org.moon.framework.beans.exception;

/**
 * Created by 明月 on 2019-02-12 / 20:25
 *
 * @email: 1814031271@qq.com
 * @Description: 注入Bean异常
 */
public class InjectBeanException extends RuntimeException {

    public InjectBeanException(String message) {
        super(message);
    }
}
