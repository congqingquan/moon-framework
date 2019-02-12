package org.moon.framework.core.constant;

import java.net.URL;

/**
 * Created by 明月   on 2019-01-13 / 22:38
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Moon-framework's Constant
 */
public class Constant {

	/**
	 * Object class instance
	 */
	public static final Class<?> OBJECT_CLASS = Object.class;

	/**
	 * Encoding format
	 */
	public interface Encoding {
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
	public interface FileType {
		/**
		 * Class
		 */
		String CLASS = "class";
	}

	/**
	 * Path constant
	 */
	public interface Path {
		/**
		 * class-path's URL instance
		 */
		URL CLASSPATH_URL = Thread.currentThread().getContextClassLoader().getResource(Empty.CHAR_SEQUENCE);
		/**
		 * class-path utf8 string
		 */
		String CLASSPATH = CLASSPATH_URL.getPath();
	}

	/**
	 * Empty properties
	 */
	public interface Empty {
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
