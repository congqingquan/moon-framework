package org.moon.framework.core.utils.web;

/**
 * Created by 明月 on 2019-02-14 / 15:38
 *
 * @email: 1814031271@qq.com
 * @Description: Web请求上下文
 */
public class WebContextHolder {
    private static ThreadLocal<Object> context = new ThreadLocal<>();

    public static void set(Object value) {
        context.set(value);
    }

    public static Object get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
