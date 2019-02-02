package org.moon.framework.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 明月   on 2019-01-13 / 23:31
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 反射工具类
 */
@SuppressWarnings("all")
public final class ReflectionUtils {

	private ReflectionUtils() {
	}

	/**
	 * 暴力访问
	 */
	private static boolean ACCESS_PERMISSIONS = Boolean.TRUE;

	/**
	 * 根据类全限名实例化对象
	 * @param qualifiedName 全限定名
	 * @return 实例化对象
	 */
	public static Object newInstance(String qualifiedName) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			try {
				return Class.forName(qualifiedName).newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据类全限名实例化对象
	 * @param qualifiedName 全限定名
	 * @param type 对象的Class类型
	 * @return 实例化对象
	 */
	public static <T> T newInstance(String qualifiedName, Class<T> type) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			try {
				return (T) Class.forName(qualifiedName).newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * <p>通过无参构造器实例化对象(源类即使不含有公开无参构造器也可以进行实例化)</p>
	 * <p>Class对象为null/对象不含有无参构造/异常返回null</p>
	 */
	public static <T> T newInstance(Class<T> clazz) {
		if (null != clazz) {
			try {
				Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[] {});
				constructor.setAccessible(ACCESS_PERMISSIONS);
				return constructor.newInstance(new Object[] {});
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <p>通过有参构造器实例化对象(源类即使不含有公开有参构造器也可以进行实例化)</p>
	 * <p>Class对象为null/构造器参数列表为空/构造器参数类型列表为空/异常返回null</p>
	 */
	public static <T> T newInstance(Class<T> clazz, Object[] args, Class<?>... argTypes) {
		if (null != clazz && null != args && args.length > 0 && null != argTypes && argTypes.length > 0) {
			try {
				Constructor<T> constructor = clazz.getDeclaredConstructor(argTypes);

				constructor.setAccessible(ACCESS_PERMISSIONS);

				return constructor.newInstance(args);
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取所有字段实例名的列表,若无字段属性则返回null
	 */
	public static String[] getFieldNames(Class<?> clazz) {
		Map<String, Field> fieldsMap = null;
		String[] matchedField = null;
		if (null != clazz) {
			// 获取类所有字段的Name与对应的Field实例
			fieldsMap = getFieldsMap(clazz);
			// 如果存有有效字段
			if (null != fieldsMap && fieldsMap.size() > 0) {
				// 拼装数据
				Collection<String> names = fieldsMap.keySet();
				names.toArray(matchedField = new String[names.size()]);
			}
		}
		return matchedField;
	}

	/**
	 * 获取所有字段实例的列表,若无字段属性则返回null
	 */
	public static Field[] getFieldInstances(Class<?> clazz) {
		Field[] fields = null;
		if (null != clazz)
			fields = clazz.getDeclaredFields();
		return fields;
	}

	/**
	 * 获取所有的字段的{Name, Filed}形式的映射对,若无字段属性则返回null
	 */
	public static Map<String, Field> getFieldsMap(Class<?> clazz) {
		Map<String, Field> map = null;
		if (null != clazz) {
			Field[] fields = clazz.getDeclaredFields();
			if (null != fields && fields.length > 0) {
				map = new HashMap<>();
				for (int i = 0; i < fields.length; i++)
					map.put(fields[i].getName(), fields[i]);
			}
		}
		return map;
	}

	/**
	 * 获取所有的静态变量名,若无则返回null
	 */
	public static String[] getStaticFieldNames(Class<?> clazz) {
		return getStaticFieldsByCondition(clazz, String.class);
	}

	/**
	 * 获取所有的静态变量名,若无则返回null
	 */
	public static Field[] getStaticFieldInstances(Class<?> clazz) {
		return getStaticFieldsByCondition(clazz, Field.class);
	}

	/**
	 * 根据条件获取字段信息 
	 */
	private static <T> T[] getStaticFieldsByCondition(Class<?> clazz, Class<T> arrayType) {
		T[] result = null;
		if (null != clazz) {
			// 获取所有的字段实例
			Field[] instances = getFieldInstances(clazz);
			if (null != instances && instances.length > 0) {
				if (arrayType == String.class) {
					String[] temp = new String[instances.length];
					int count = 0;
					for (int i = 0; i < instances.length; i++) {
						Field field = instances[i];
						if (Modifier.isStatic(field.getModifiers()))
							temp[count++] = field.getName();
					}
					System.arraycopy(temp, 0, (result = (T[]) new String[count]), 0, count);
				} else if (arrayType == Field.class) {
					Field[] temp = new Field[instances.length];
					int count = 0;
					for (int i = 0; i < instances.length; i++) {
						Field field = instances[i];
						if (Modifier.isStatic(field.getModifiers()))
							temp[count++] = field;
					}
					System.arraycopy(temp, 0, (result = (T[]) new Field[count]), 0, count);
				}
			}
		}
		return result;
	}

	/**
	 * 获取所有字段实例名的列表,若无字段属性则返回null
	 */
	public static String[] getMethodNames(Class<?> clazz) {
		Map<String, Method> methodsMap = null;
		String[] matchedMethod = null;
		if (null != clazz) {
			// 获取类所有字段的Name与对应的Field实例
			methodsMap = getMethodsMap(clazz);
			// 如果存有有效字段
			if (null != methodsMap && methodsMap.size() > 0) {
				// 拼装数据
				Collection<String> names = methodsMap.keySet();
				names.toArray(matchedMethod = new String[names.size()]);
			}
		}
		return matchedMethod;
	}

	/**
	 * 获取所有字段实例的列表,若无字段属性则返回null
	 */
	public static Method[] getMethodInstances(Class<?> clazz) {
		Method[] methods = null;
		if (null != clazz)
			methods = clazz.getDeclaredMethods();
		return methods;
	}

	/**
	 * 获取所有的字段的{Name, Filed}形式的映射对,若无字段属性则返回null
	 */
	public static Map<String, Method> getMethodsMap(Class<?> clazz) {
		Map<String, Method> methodMap = null;
		if (null != clazz) {
			Method[] methods = clazz.getDeclaredMethods();
			if (null != methods && methods.length > 0) {
				methodMap = new HashMap<>();
				for (int i = 0; i < methods.length; i++)
					methodMap.put(methods[i].getName(), methods[i]);
			}
		}
		return methodMap;
	}

	/**
	 * 根据方法名获取Method实例,若方法名无效则返回null 
	 */
	public static Method getMethodByName(Class<?> clazz, String methodName) {
		if (null != clazz && StringUtils.isNotEmpty(methodName)) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++)
				if (methods[i].getName().equals(methodName))
					return methods[i];
		}
		return null;
	}

	/**
	 * 从methods中获取标记了指定注解的单个或多个Method实例
	 */
	public static Method[] getMethodByAnnotation(Method[] methods, Class<? extends Annotation> annotation) {
		Method[] result = null;
		if (null != annotation && ArrayUtils.isNotEmpty(methods)) {
			result = new Method[methods.length];
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				method.setAccessible(Boolean.TRUE);
				if (method.getDeclaredAnnotation(annotation) != null) {
					result[i] = method;
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 去除空数据并返回
	 * @param source 源数组
	 */
	public static Method[] removeMethodArrayEmptyElement(Method[] source) {
		Method[] result = null;
		if (ArrayUtils.isNotEmpty(source)) {
			result = new Method[ArrayUtils.validDataLength(source)];
			for (int i = 0, j = 0; i < source.length; i++) {
				if (null != source[i]) {
					result[j++] = source[i];
				}
			}
			return result;
		}
		return result;
	}

	/**
	 * 执行无参无返回值结果的函数
	 */
	public static void invoke(Object instance, String methodName)
			throws IllegalArgumentException, InvocationTargetException {
		invoke0(instance, methodName, null, new Object[] {});
	}

	/**
	 * 执行无参有返回值结果的函数
	 */
	public static <R> R invoke(Object instance, String methodName, Class<R> clazz)
			throws IllegalArgumentException, InvocationTargetException {
		return invoke0(instance, methodName, clazz, new Object[] {});
	}

	/**
	 * 执行有参无返回值函数
	 */
	public static void invoke(Object instance, String methodName, Object... methodArgs)
			throws IllegalArgumentException, InvocationTargetException {
		invoke0(instance, methodName, null, methodArgs);
	}

	/**
	 * 有参有返回值
	 */
	public static <R> R invoke(Object instance, String methodName, Class<R> retType, Object... methodArgs)
			throws IllegalArgumentException, InvocationTargetException {
		return invoke0(instance, methodName, retType, methodArgs);
	}

	/**
	 * <p>根据指定函数名称/参数列表/执行实例执,行实例参数上的对应函数.</p>
	 * 
	 * <p>谨慎执行该函数:</p>
	 * 
	 * <p>1.若retType参数与函数执行后返回的结果类型不一致,需要手动捕捉异常并处理.</p>
	 * <p>2.若methodArgs参数与执行的函数的参数列表不符合,需要手动捕捉异常并处理.</p>
	 * <p>3.若实例参数为null或者MethodName无效函数不会执行且返回null</p>
	 * <p>4.若方法没有返回值则返回null</p>
	 */
	public static <R> R invoke0(Object instance, String methodName, Class<R> retType, Object... methodArgs)
			throws IllegalArgumentException, InvocationTargetException {
		// 返回值
		R retVal = null;

		// 校验实例不为null且方法名有效
		if (null == instance || StringUtils.isEmpty(methodName))
			throw new IllegalArgumentException("instace is empty or methodName is invalid");

		try {
			Method method = getMethodByName(instance.getClass(), methodName);
			// 设置访问权限
			method.setAccessible(ACCESS_PERMISSIONS);
			Object result;
			result = method.invoke(instance, methodArgs);
			// 由于范型的擦拭,此处不会抛出ClassCastException,会在调用后为将方法的返回值赋值给成员或局部变量时抛出异常
			// 故直接在函数内部进行检测
			if (null == result && null != retType)
				throw new IllegalArgumentException("param: retType is wrong");
			if (null != result && result.getClass() != retType)
				throw new IllegalArgumentException("param: retType is wrong");
			else
				retVal = (R) result;
		} catch (IllegalAccessException e) {
			// TODO -> 该异常不会发生
		}
		return retVal;
	}

	/**
	 * 排除覆盖父类的字段
	 */
	private static Field[] excludeSuperClassField(Class<?> superClass, Field[] childrenFields) {
		// 返回结果
		List<Field> fieldList = null;
		if (null != superClass) {
			// 父类的所有字段
			Field[] superFields = superClass.getDeclaredFields();
			// 如果父类存有有效字段
			if (null != superFields && superFields.length > 0) {
				// 实例化返回结果
				fieldList = new ArrayList<>();
				// 移除
				for (int i = 0; i < childrenFields.length; i++) {
					// 暂无
				}
			}
		}
		return null;
	}
}