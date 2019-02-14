package org.moon.framework.core.utils.basic;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by 明月   on 2019-01-13 / 23:21
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 异常工具类
 */
public final class ExceptionUtils {

	private ExceptionUtils() {
	}

	/**
	 * 异常信息转为String格式
	 */
	public static String getStackTrace(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer, true));
		return writer.toString();
	}
}
