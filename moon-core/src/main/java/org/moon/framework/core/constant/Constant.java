package org.moon.framework.core.constant;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by 明月   on 2019-01-13 / 22:38
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Moon-framework's Constant
 */
public class Constant {

	/**
	 * Encoding format
	 */
	public static interface Encoding {
		/**
		 * UTF8
		 */
		String UTF8 = "UTF-8";

		/**
		 * GBK
		 */
		String GBK = "gbk";

		/**
		 * ISO-8859-1
		 */
		String ISO_8859_1 = "ISO-8859-1";
	}

	/**
	 * File type
	 */
	public static interface FileType {
		/**
		 * Class
		 */
		String CLASS = "class";
	}

	/**
	 * Path constant
	 */
	public static interface Path {
		/**
		 * class-path's URL instance
		 */
		URL CLASSPATH_URL = Thread.currentThread().getContextClassLoader().getResource("");
		/**
		 * class-path utf8 string
		 */
		String CLASSPATH_UTF8 = convertClassPath(CLASSPATH_URL.getPath(), Encoding.UTF8);

		static String convertClassPath(String path,String encoding) {
			try {
				return URLDecoder.decode(path, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("溶解ClassPath为UTF8编码失败");
			}
		}
	}

	/**
	 * Empty properties
	 */
	public static interface Empty {
		/**
		 * instance
		 */
		Object INSTANCE = null;
		/**
		 * char sequence
		 */
		String CHAR_SEQUENCE = "";
	}
}
