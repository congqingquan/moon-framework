package org.moon.framework.core.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by 明月   on 2019-01-13 / 23:21
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Properties文件的工具类
 */
public final class PropertiesUtils {

	public static final String ENCODE_UTF8 = "UTF-8";

	private PropertiesUtils() {
	}

	/**
	 * @param fileClassPath
	 * @return java.util.Properties 路径有误返回null
	 * @throws
	 * @description 根据文件的classpath路径加载文件并返回Properties实例
	 * @date 2018/8/28 23:42
	 * @author C99
	 */
	public static Properties load(String fileClassPath) {

		if (StringUtils.isEmpty(fileClassPath))
			return null;

		Properties properties = new Properties();
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream(fileClassPath), ENCODE_UTF8);
			properties.load(reader);
		} catch (Exception e) {
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}
