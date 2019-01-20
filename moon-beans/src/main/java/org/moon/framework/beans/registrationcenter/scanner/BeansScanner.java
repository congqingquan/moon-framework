package org.moon.framework.beans.registrationcenter.scanner;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.description.helper.BeanDescriptionGenerateHelper;
import org.moon.framework.core.constant.Constant;
import org.moon.framework.core.utils.Assert;
import org.moon.framework.core.utils.StringUtils;

/**
 * Created by 明月   on 2019-01-13 / 22:46
 *
 * @email: 1814031271@qq.com
 * 
 * @Description: Beans扫描器
 */
public class BeansScanner {

	private static BeanDescriptionGenerateHelper beanDescriptionGenerateHelper = BeanDescriptionGenerateHelper.get();

	/**
	 * 扫描根路径下的所有class文件
	 * 
	 * @param rootPath
	 *            扫描的根据经
	 * @return class文件的路径集合
	 */
	public static List<String> getClassPathsByRootPath(String rootPath) {
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
			return beanDescriptionGenerateHelper.generate(clazz);
		return null;
	}

	/**
	 * 校验Class上是否标注了指定注解
	 * @param clazz 需要检验的Class对象
	 * @param annotationClasses 指定注解集合
	 * @return 
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
	 * 根据扫描路径扫描Class并获取Class的全限定名
	 * 
	 * @param scanClassPath 扫描路径
	 * @return 全限定名集合
	 */
	public static List<String> getQualifiedNames(String scanClassPath) {
		// 1. 实例化装载磁盘上classPath下的所有Class文件的全限定名
		List<String> qualifiedNames = new ArrayList<>();
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
					// 删除后缀后添加到全限定名容器内
					qualifiedNames.add(delClassPostfix(next));
				}
			}
		}
		return qualifiedNames;
	}

	/**
	 * 扫描路径参数下的所有实现了annotationClass参数类型注解的类
	 * 
	 * @param scanPath
	 *            扫描路径
	 * @return 若路径无效或路径下无符合的component则返回空的List集合
	 */
	public static List<BeanDescription> scan(String scanClassPath) {

		Assert.isEmptyString(scanClassPath, "组件扫描路径不能为空!!!");
		
		// 1. 初始化装载BeanDescription的容器
		List<BeanDescription> beanDescriptions = new ArrayList<>();

		// 2. 获取路径下所有Class的全限定名
		List<String> qualifiedNames = getQualifiedNames(scanClassPath);

		// 3. 根据这些全限定名获取所有实现了annotationClass参数类型注解的类的BeanDescription
		for (Iterator<String> iterator = qualifiedNames.iterator(); iterator.hasNext();) {
			String qualifiedName = iterator.next();
			// 4. 根据全限定名加载Class实例
			Class<?> loadClass = null;
			try {
				loadClass = Class.forName(qualifiedName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// 
			
			
			// 可装载
//			if (isLoadable(clazz, annotationClasses))
//				return beanDescriptionGenerateHelper.generate(clazz);
//			
//			BeanDescription loadedBeanDescription = loadBeanDescription(loadClass, annotationClasses);
//			if (null != loadedBeanDescription)
//				beanDescriptions.add(loadedBeanDescription);
//			else
//				continue;
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
		scan(basePackage);
		// 打印
	}
}