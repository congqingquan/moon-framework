package org.moon.framework.beans.exception;

/**
 * Created by 明月 on 2019-02-12 / 16:56
 *
 * @email: 1814031271@qq.com
 * @Description: No unique bean exception
 */
public class NoUniqueBeanException extends RuntimeException {

    public NoUniqueBeanException(String message) {
        super(message);
    }
}
