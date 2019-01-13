package org.moon.framework.beans.registrationcenter;

/**
 * Created by 明月   on 2019-01-13 / 21:37
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 注册中心
 */
public interface RegistrationCenter<T> {
	
	void register(T t);
	
	void remove(T t);
}
