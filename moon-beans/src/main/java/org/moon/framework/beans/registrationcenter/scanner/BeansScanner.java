//package org.moon.framework.beans.registrationcenter.scanner;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.lang.annotation.Annotation;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.moon.framework.core.constant.Constant;
//import org.moon.framework.core.constant.Constant.Path;
//import org.moon.framework.core.utils.StringUtils;
//
///**
// * Created by 明月   on 2019-01-13 / 22:46
// *
// * @email: 1814031271@qq.com
// * 
// * @Description: Beans扫描器
// */
//public class BeansScanner {
//
//	/**
//	 * 扫描根路径下的所有class文件
//	 * 
//	 * @param rootPath
//	 *            扫描的根据经
//	 * @return class文件的路径集合
//	 */
//	private static List<String> getClassPathsByRootPath(String rootPath) {
//		List<String> classesPath = null;
//		if (StringUtils.isNotEmpty(rootPath)) {
//			classesPath = new ArrayList<>();
//			File[] listFiles = new File(rootPath).listFiles();
//			if (null != listFiles) {
//				for (int i = 0; i < listFiles.length; i++)
//					addBeanPathToList(listFiles[i], classesPath);
//			} else
//				throw new RuntimeException("根路径有误！");
//		}
//		return classesPath;
//	}
//
//	/**
//	 * 递归扫描class文件路径并装入集合内
//	 * 
//	 * @param file 文件实例
//	 * @param classesPath 存储集合
//	 */
//	private static void addBeanPathToList(File file, List<String> classPaths) {
//		if (null != file && null != classPaths) {
//			if (file.isDirectory()) {
//				File[] listFiles = file.listFiles();
//				for (int i = 0; i < listFiles.length; i++) {
//					addBeanPathToList(listFiles[i], classPaths);
//				}
//			} else {
//				String fileName = file.getName();
//				fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
//				if (fileName.equals(Constant.FileType.CLASS))
//					classPaths.add(file.getAbsolutePath());
//			}
//		}
//	}
//
//	/**
//	 * 根据全限定名反射获取class对象
//	 * 
//	 * @param qualifiedName
//	 *            全限定名
//	 * @return class对象,若限定路径无效返回null
//	 */
//	private static Class<?> instantiatedObjectByQualifiedName(String qualifiedName) {
//		Class<?> forName = null;
//		if (StringUtils.isNotEmpty(qualifiedName)) {
//			try {
//				forName = Class.forName(qualifiedName);
//			} catch (ClassNotFoundException e) {
//				return forName;
//			}
//		}
//		return forName;
//	}
//
//	/**
//	 * 校验该Class实例是否实现了参数annotationClass类型的类级别的注解
//	 * 
//	 * @param clazz
//	 *            校验的class实例
//	 * @return 校验结果,参数为空返回null
//	 */
//	private static boolean isComponentClass(Class<?> clazz, Class<? extends Annotation> annotationClass) {
//		if (null != clazz)
//			return null == clazz.getDeclaredAnnotation(annotationClass) ? false : true;
//		return false;
//	}
//
//	/**
//	 * 通过class实例进行调用无参构造器实例化
//	 * 
//	 * @return 实例化对象,失败返回null
//	 */
//	private static Object instantiateComponentObject(Class<?> clazz) {
//		Object newInstance = null;
//		try {
//			newInstance = clazz.newInstance();
//		} catch (InstantiationException e) {
//			return newInstance;
//		} catch (IllegalAccessException e) {
//			return newInstance;
//		}
//		return newInstance;
//	}
//
//	/**
//	 * 扫描路径参数下的所有实现了annotationClass参数类型注解的类
//	 * 
//	 * @param scanPath
//	 *            扫描路径
//	 * @return 若路径无效或路径下无符合的component则返回空的List集合
//	 */
//	public static List<Object> sacnComponents(String scanPath, Class<? extends Annotation> annotationClass) {
//		// 实例化装载component的容器
//		List<Object> components = new ArrayList<>();
//		if (isNotEmptyString(scanPath)) {
//			// .转换为/
//			String converterScanPath = convertedPathToClassStandardToFileStandard(scanPath);
//			if (isNotEmptyString(converterScanPath)) {
//				// 转换为UTF-8编码字符
//				scanPath = converterPathEncodeToUTF8(CLASS_PATH + converterScanPath);
//				// 获取路径下的所有类文件的路径
//				if (isNotEmptyString(scanPath)) {
//					List<String> classPaths = getClassPathsByRootPath(scanPath);
//					// 获取'标准'的class-path路径
//					String standardClassPath = convertedStandardFormatToFilePath(CLASS_PATH_UTF_8);
//					// 获取上步中所有实现了annotationClass参数注解的Class实例
//					String next;
//					for (Iterator<String> iterator = classPaths.iterator(); iterator.hasNext();) {
//						next = iterator.next();
//						// 截取绝对路径中的类文件的全限定名字符
//						next = next.substring(next.lastIndexOf(standardClassPath) + standardClassPath.length());
//						// 将文件格式的类全限定名字符进行转换
//						next = convertedPathToFileStandardToClassStandard(next);
//						// 根据全限定名获取class对象
//						Class<?> clazz = instantiatedObjectByQualifiedName(delClassPostfix(next));
//						// 实例化实现了annotationClass参数注解的实例
//						if (null != clazz) {
//							if (isComponentClass(clazz, annotationClass)) {
//								Object obj = instantiateComponentObject(clazz);
//								if (null != obj)
//									components.add(obj);
//							} else
//								continue;
//						}
//					}
//				}
//			}
//		}
//		return components;
//	}
//
//	/**
//	 * 转换类路径的格式(. -> /)
//	 * 
//	 * @param path
//	 *            转换路径
//	 * @return 转换结果
//	 */
//	private static String convertedPathToClassStandardToFileStandard(String path) {
//		if (isNotEmptyString(path))
//			return path.trim().replace(".", "/");
//		return null;
//	}
//
//	/**
//	 * 转换类路径的格式(\ -> .)
//	 * 
//	 * @param path
//	 *            转换路径
//	 * @return 转换结果
//	 */
//	private static String convertedPathToFileStandardToClassStandard(String path) {
//		if (isNotEmptyString(path))
//			return path.trim().replace("\\", ".");
//		return null;
//	}
//
//	/**
//	 * 转换文件路径的格式 / -> \,且文件首字母为/会被去除
//	 * 
//	 * @param filePath 文件路径
//	 * @return 转换结果
//	 */
//	private static String convertedStandardFormatToFilePath(String filePath) {
//		if (isNotEmptyString(filePath)) {
//			String result = filePath.replaceAll("/", "\\\\");
//			if (result.startsWith("\\")) {
//				return converterPathEncodeToUTF8(result.substring(1));
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 删除class文件后缀名
//	 * 
//	 * @param qualifiedName
//	 *            全限定名
//	 * @return 删除后的结果
//	 */
//	private static String delClassPostfix(String qualifiedName) {
//		if (isNotEmptyString(qualifiedName)) {
//			String substring = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
//			if (isNotEmptyString(substring))
//				return substring;
//		}
//		return null;
//	}
//
//	/**
//	 * 转换参数路径为UTF-8编码格式
//	 * 
//	 * @param path
//	 *            路径
//	 * @return 编码后的结果字符，转换异常结果为null
//	 */
//	private static String converterPathEncodeToUTF8(String path) {
//		String result = null;
//		try {
//			result = URLDecoder.decode(path, UTF_8);
//		} catch (UnsupportedEncodingException e) {
//			return null;
//		}
//		if (isNotEmptyString(result))
//			return result;
//		return null;
//	}
//}