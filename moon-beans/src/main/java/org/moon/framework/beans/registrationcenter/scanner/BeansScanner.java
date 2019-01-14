package org.moon.framework.beans.registrationcenter.scanner;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.moon.framework.beans.description.BeanDescription;
import org.moon.framework.beans.description.helper.BeanDescriptionHelper;
import org.moon.framework.core.constant.Constant;
import org.moon.framework.core.utils.StringUtils;

/**
 * Created by 明月   on 2019-01-13 / 22:46
 *
 * @email: 1814031271@qq.com
 * 
 * @Description: Beans扫描器
 */
public class BeansScanner {

	/**
	 * 扫描根路径下的所有class文件
	 * 
	 * @param rootPath
	 *            扫描的根据经
	 * @return class文件的路径集合
	 */
	private static List<String> getClassPathsByRootPath(String rootPath) {
		List<String> classesPath = null;
		if (StringUtils.isNotEmpty(rootPath)) {
			classesPath = new ArrayList<>();
			File[] listFiles = new File(rootPath).listFiles();
			if (null != listFiles) {
				for (int i = 0; i < listFiles.length; i++)
					addBeanPathToList(listFiles[i], classesPath);
			} else
				throw new RuntimeException("根路径有误！");
		}
		return classesPath;
	}

	/**
	 * 递归扫描class文件路径并装入集合内
	 * 
	 * @param file 文件实例
	 * @param classesPath 存储集合
	 */
	private static void addBeanPathToList(File file, List<String> classPaths) {
		if (null != file && null != classPaths) {
			if (file.isDirectory()) {
				File[] listFiles = file.listFiles();
				for (int i = 0; i < listFiles.length; i++) {
					addBeanPathToList(listFiles[i], classPaths);
				}
			} else {
				String fileName = file.getName();
				fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (fileName.equals(Constant.FileType.CLASS))
					classPaths.add(file.getAbsolutePath());
			}
		}
	}

	/**
	 * 根据全限定名反射获取class对象
	 * 
	 * @param qualifiedName
	 *            全限定名
	 * @return class对象,若限定路径无效返回null
	 */
	private static Class<?> instantiatedObjectByQualifiedName(String qualifiedName) {
		Class<?> forName = null;
		if (StringUtils.isNotEmpty(qualifiedName)) {
			try {
				forName = Class.forName(qualifiedName);
			} catch (ClassNotFoundException e) {
				return forName;
			}
		}
		return forName;
	}

	/**
	 * 校验该Class实例是否实现了参数annotationClass类型的类级别的注解
	 * 
	 * @param clazz
	 *            校验的class实例
	 * @return 校验结果,参数为空返回null
	 */
	@SafeVarargs
	private static BeanDescription loadBeanDescription(Class<?> clazz,
			Class<? extends Annotation>... annotationClasses) {
		if (null == clazz || null == annotationClasses || annotationClasses.length <= 0)
			return null;
		// 可装载
		if (isLoadable(clazz, annotationClasses))
			return BeanDescriptionHelper.create(clazz);
		return null;
	}

	/**
	 * 校验Class上是否标注了指定注解
	 * @param clazz 需要检验的Class对象
	 * @param annotationClasses 指定注解集合
	 * @return
	 */
	@SafeVarargs
	private static boolean isLoadable(Class<?> clazz, Class<? extends Annotation>... annotationClasses) {
		for (int i = 0; i < annotationClasses.length; i++) {
			if (clazz.getDeclaredAnnotation(annotationClasses[i]) == null)
				continue;
			else
				return true;
		}
		return false;
	}

	/**
	 * 扫描路径参数下的所有实现了annotationClass参数类型注解的类
	 * 
	 * @param scanPath
	 *            扫描路径
	 * @return 若路径无效或路径下无符合的component则返回空的List集合
	 */
	@SafeVarargs
	public static List<BeanDescription> scan(String scanClassPath, Class<? extends Annotation>... annotationClasses) {
		// 1. 实例化装载Bean描述的集合
		List<BeanDescription> beanDescriptions = new ArrayList<>();
		if (StringUtils.isNotEmpty(scanClassPath)) {
			// 2. 声明class-path路径
			String standardClassPath = convertedStandardFormatToFilePath(Constant.Path.CLASSPATH_UTF8);
			// 3. '.'转换为'/'
			String converterScanPath = convertClassPathToFilePath(scanClassPath);
			if (StringUtils.isNotEmpty(converterScanPath)) {
				// 4. 转换为UTF-8编码字符
				scanClassPath = Constant.Path.convertClassPath(Constant.Path.CLASSPATH_UTF8 + scanClassPath);
				// 获取路径下的所有类文件的路径
				List<String> classPaths = getClassPathsByRootPath(scanClassPath);
				// 获取上步中所有实现了annotationClasses参数注解的类的BeanDescription
				String next;
				for (Iterator<String> iterator = classPaths.iterator(); iterator.hasNext();) {
					next = iterator.next();
					// 截取绝对路径中的类文件的全限定名字符
					next = next.substring(next.lastIndexOf(standardClassPath) + standardClassPath.length());
					// 将文件格式的类全限定名字符进行转换
					next = convertedPathToFileStandardToClassStandard(next);
					// 根据全限定名获取class对象
					Class<?> clazz = instantiatedObjectByQualifiedName(delClassPostfix(next));
					// 实例化实现了指定参数注解的实例
					if (null != clazz) {
						BeanDescription loadedBeanDescription = loadBeanDescription(clazz, annotationClasses);
						if (null != loadedBeanDescription)
							beanDescriptions.add(loadedBeanDescription);
						else
							continue;
					}
				}
			}
		}
		return beanDescriptions;
	}

	/**
	 * 转换类路径的格式(. -> /)
	 * 
	 * @param path
	 *            转换路径
	 * @return 转换结果
	 */
	private static String convertClassPathToFilePath(String classPath) {
		if (StringUtils.isNotEmpty(classPath))
			return classPath.trim().replace(".", "/");
		return null;
	}

	/**
	 * 转换类路径的格式(\ -> .)
	 * 
	 * @param path
	 *            转换路径
	 * @return 转换结果
	 */
	private static String convertedPathToFileStandardToClassStandard(String path) {
		if (StringUtils.isNotEmpty(path))
			return path.trim().replace("\\", ".");
		return null;
	}

	/**
	 * 转换文件路径的格式 / -> \,且文件首字母为/会被去除
	 * 
	 * @param filePath 文件路径
	 * @return 转换结果
	 */
	private static String convertedStandardFormatToFilePath(String filePath) {
		if (StringUtils.isNotEmpty(filePath)) {
			String result = filePath.replaceAll("/", "\\\\");
			if (result.startsWith("\\")) {
				return result.substring(1);
			}
		}
		return null;
	}

	/**
	 * 删除class文件后缀名
	 * 
	 * @param qualifiedName
	 *            全限定名
	 * @return 删除后的结果
	 */
	private static String delClassPostfix(String qualifiedName) {
		if (StringUtils.isNotEmpty(qualifiedName)) {
			String substring = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
			if (StringUtils.isNotEmpty(substring))
				return substring;
		}
		return null;
	}

	public static void main(String[] args) {
		// 扫描路径
		String basePackage = "org.moon.framework.beans";

		// 扫描(注解类可以自定义传递,并不一定用@Component,可以自己实现一个类注解,但是根据传递路径扫描的类也只会是实现了此处传递的注解的类)
		scan(basePackage, new Class[] {});
		// 打印
	}
}