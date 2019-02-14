package org.moon.framework.core.utils.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.moon.framework.core.utils.basic.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Created by 明月   on 2018-12-12 / 11:01
 *
 * @Description: 操作Cookie的工具类
 */
public final class CookieUtils {

	/**
	 * UTF-8编码
	 */
	private static final String ENCODE_UTF8 = "UTF-8";

	/**
	 * 空字符
	 */
	private static final String EMPTY_CHARACTER = "";

	/**
	 * Cookie所在域名
	 */
	private static final String COOKIE_DOMAIN = "localhost";

	/**
	 * 默认Cookie所在路径
	 */
	private static final String DEFAULT_COOKIE_PATH = "/";

	/**
	 * 默认过期时间(7天)
	 */
	private static final Integer DEFAULT_COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

	/**
	 * 默认的Cookie值
	 */
	private static final String DEFAULT_COOKIE_VALUE = EMPTY_CHARACTER;

	/**
	 * 不记录Cookie
	 */
	private static final Integer NO_RECORD_COOKIE = 0;

	/**
	 * 会话级别Cookie
	 */
	private static final Integer SESSION_LEVEL_COOKIE = -1;

	private CookieUtils() {
	}

	/**
	 * Cookie[]的一元操作
	 * 
	 * @param unaryOperation 针对于Cookie的操作函数
	 */
	public static <R> R unaryOperationOfCookie(Cookie[] cookies, Function<Cookie[], R> unaryOperation) {
		return unaryOperation.apply(cookies);
	}

	/**
	 * 校验Cookie数组内含有Cookie
	 */
	public static boolean hasCookie(Cookie[] cookies) {
		return null != cookies && cookies.length > 0;
	}

	/**
	 * 根据cookieName获取Cookie实例,cookieName无效返回null
	 */
	public static Cookie getCookie(Cookie[] cookies, String cookieName) {
		if (StringUtils.isNotEmpty(cookieName) && hasCookie(cookies))
			for (int i = 0; i < cookies.length; i++)
				if (cookies[i].getName().equals(cookieName))
					return cookies[i];
		return null;
	}

	/**
	 * 根据CookieName获取CookieVal,CookieName无效、内部异常则返回null
	 */
	public static String getCookieValue(Cookie[] cookies, String cookieName) {
		Cookie cookie = getCookie(cookies, cookieName);
		if (null != cookie) {
			try {
				return URLDecoder.decode(cookie.getValue(), ENCODE_UTF8);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取Cookie信息(参数为空返回null)
	 */
	public static String getCookieInfo(Cookie cookie) {
		if (null != cookie) {
			JSONObject info = new JSONObject();
			info.put("name", cookie.getName());
			info.put("value", cookie.getValue());
			info.put("path", cookie.getPath());
			info.put("maxAge", cookie.getMaxAge());
			info.put("domain", cookie.getDomain());
			info.put("httpOnly", cookie.isHttpOnly());
			info.put("secure", cookie.getSecure());
			return info.toJSONString();
		}
		return null;
	}

	/**
	 * 获取多个Cookie的信息(参数为空返回null)
	 */
	public static String getCookiesInfo(Cookie[] cookies) {
		if (null != cookies && cookies.length > 0) {
			JSONArray cookiesInfo = new JSONArray();
			for (int i = 0; i < cookies.length; i++)
				cookiesInfo.add(getCookieInfo(cookies[i]));
			return cookiesInfo.toJSONString();
		}
		return null;
	}

	/**
	 * 删除Cookie
	 */
	public static boolean setEmpty(Cookie cookie) {
		if (null != cookie) {
			cookie.setValue(DEFAULT_COOKIE_VALUE);
			cookie.setMaxAge(NO_RECORD_COOKIE);
			cookie.setPath(DEFAULT_COOKIE_PATH);
			return true;
		}
		return false;
	}

	/**
	 * 删除Cookie根据CookieName
	 */
	public static boolean deleteCookie(Cookie[] cookies, String cookieName) {
		return setEmpty(getCookie(cookies, cookieName));
	}

	/**
	 * 删除所有Cookie(置空全部Cookie)
	 */
	public static boolean deleteAllCookie(Cookie[] cookies) {
		if (null != cookies) {
			Arrays.stream(cookies).forEach(CookieUtils::setEmpty);
			return true;
		}
		return false;
	};

	/**
	 * 响应中追加设置Cookie,response & cookieName无效则返回false
	 * 
	 * @param response 请求响应实例
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 */
	public static boolean setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
		return setCookie(response, cookieName, cookieValue, null, null);
	}

	/**
	 * 响应中追加设置Cookie,response & cookieName无效则返回false
	 * 
	 * @param response 请求响应实例
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 * @param maxAge 过期时间,若传参为null则设置默认过期时间(7天)
	 */
	public static boolean setCookie(HttpServletResponse response, String cookieName, String cookieValue,
			Integer maxAge) {
		return setCookie(response, cookieName, cookieValue, maxAge, null);
	}

	/**
	 * 响应中追加设置Cookie,response & cookieName无效则返回false
	 * 
	 * @param response 请求响应实例
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 * @param maxAge 过期时间,若传参为null则设置默认过期时间(7天)
	 * @param path 有效路径
	 */
	public static boolean setCookie(HttpServletResponse response, String cookieName, String cookieValue, Integer maxAge,
			String path) {
		if (null != response && StringUtils.isNotEmpty(cookieName)) {
			// value
			if (null == cookieValue)
				cookieValue = DEFAULT_COOKIE_VALUE;
			// max age
			if (null == maxAge)
				maxAge = DEFAULT_COOKIE_MAX_AGE;
			else if (maxAge == 0)
				maxAge = NO_RECORD_COOKIE;
			else if (maxAge < 0)
				maxAge = SESSION_LEVEL_COOKIE;
			// path
			if (StringUtils.isEmpty(path))
				path = DEFAULT_COOKIE_PATH;
			try {
				Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, ENCODE_UTF8));
				cookie.setMaxAge(maxAge);
				cookie.setPath(path);
				response.addCookie(cookie);
			} catch (UnsupportedEncodingException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 响应中追加设置Cookie
	 * 
	 * @param response 请求响应实例
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 * @param domain cookie的有效域
	 */
	private static boolean setSpecificCookie(HttpServletResponse response, String cookieName, String cookieValue,
			String domain, boolean isHttpOnly, boolean isHttpsCookie) {
		try {
			Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, ENCODE_UTF8));
			// HTTP only
			cookie.setHttpOnly(isHttpOnly);
			// set domain
			if (StringUtils.isNotEmpty(domain))
				cookie.setDomain(domain);
			// is https cookie
			cookie.setSecure(isHttpsCookie);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		return true;
	}

	/**
	 * 响应中追加设置Cookie,response & cookieName无效则返回false
	 * 
	 * @param response 请求响应实例
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 */
	public static boolean setHttpOnlyCookie(HttpServletResponse response, String cookieName, String cookieValue) {
		if (null != response && StringUtils.isNotEmpty(cookieName))
			return setSpecificCookie(response, cookieName, cookieValue, null, true, false);
		return false;
	}

	/**
	 * 响应中追加设置Cookie,response & cookieName无效则返回false
	 * 
	 * @param response 请求响应实例
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 */
	public static boolean setHttpOnlyDomainCookie(HttpServletResponse response, String cookieName, String cookieValue) {
		if (null != response && StringUtils.isNotEmpty(cookieName))
			return setSpecificCookie(response, cookieName, cookieValue, COOKIE_DOMAIN, true, false);
		return false;
	}
}