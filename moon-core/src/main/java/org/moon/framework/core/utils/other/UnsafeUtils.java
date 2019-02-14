package org.moon.framework.core.utils.other;

import org.moon.framework.core.utils.basic.ExceptionUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by 明月   on 2019-01-13 / 23:21
 *
 * @email: 1814031271@qq.com
 *
 * @Description: Unsafe的工具类
 */
@SuppressWarnings({ "restriction", "unused", "unchecked" })
public final class UnsafeUtils {

	/**
	 * Class Unsafe : 可以用来在任意内存地址位置处读写数据.可见,对于普通用户来说,使用起来还是比较危险的.另外,还支持一些CAS原子操作.
	 */

	private static final String THE_UNSAFE_FIELD = "theUnsafe";

	private static final Class<Unsafe> UNSAFE_CLASS = Unsafe.class;

	private static final Unsafe UNSAFE_INSTANCE = createUnsafeInstanceByField();

	private UnsafeUtils() {
	}

	/**
	 * 创建Unsafe实例，异常返回null
	 */
	private static Unsafe createUnsafeInstance() {

		Constructor<Unsafe> constructor;
		try {
			constructor = UNSAFE_CLASS.getDeclaredConstructor();
			constructor.setAccessible(true);
			if (null != constructor)
				return constructor.newInstance();
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	/**
	 * 创建Unsafe实例，异常返回null
	 */
	private static Unsafe createUnsafeInstanceByField() {
		Field theUnsafeField;
		try {
			theUnsafeField = UNSAFE_CLASS.getDeclaredField(THE_UNSAFE_FIELD);
			theUnsafeField.setAccessible(true);
			if (null != theUnsafeField)
				return (Unsafe) theUnsafeField.get(null);
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	/**
	 * 获取Unsafe实例
	 */
	public static Unsafe getInstance() {
		return UNSAFE_INSTANCE;
	}

	/**
	 * 构建对象实例(通过native的c/c++函数创建实例对象,并在JVM-Deep中开辟内存空间.不会如反射一样默认会通过无参构造器创建对象,纯底层代码实现),失败返回null
	 */
	public static <T> T newObject(Class<T> clazz) {
		try {
			return (T) UNSAFE_INSTANCE.allocateInstance(clazz);
		} catch (InstantiationException e) {
			return null;
		}
	}

	/**
	 * 修改对象的字段值
	 * 
	 * @param obj 源对象
	 * @param fieldName 字段名称
	 * @param updateValue 更新的值
	 * @return
	 */
	public static <T> boolean updateObjectFieldValue(Object obj, String fieldName, Object updateValue) {
		try {
			// 获取字段在内存中的偏移量
			long objectFieldOffset = UNSAFE_INSTANCE.objectFieldOffset(obj.getClass().getDeclaredField(fieldName));
			// 更新
			UNSAFE_INSTANCE.putObject(obj, objectFieldOffset, updateValue);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("update field exception -> " + ExceptionUtils.getStackTrace(e));
		} catch (SecurityException e) {
			throw new RuntimeException("update field exception -> " + ExceptionUtils.getStackTrace(e));
		}
		return true;
	}
}