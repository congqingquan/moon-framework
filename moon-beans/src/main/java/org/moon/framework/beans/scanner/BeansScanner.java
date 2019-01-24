package org.moon.framework.beans.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.moon.framework.beans.description.basic.BeanDescription;
import org.moon.framework.beans.parser.BeanConfigurationParser;
import org.moon.framework.beans.parser.BeanDescriptionParser;
import org.moon.framework.core.constant.Constant;
import org.moon.framework.core.constant.Constant.Encoding;
import org.moon.framework.core.utils.Assert;
import org.moon.framework.core.utils.StringUtils;
import org.moon.framework.core.utils.SystemUtils;

/**
 * Created by 明月   on 2019-01-13 / 22:46
 *
 * @email: 1814031271@qq.com
 * 
 * @Description: Beans扫描器
 */
public class BeansScanner {

	/**
	 * Bean描述解析组件
	 */
	private BeanDescriptionParser beanDescriptionParser = new BeanDescriptionParser();

	/**
	 * Bean配置解析组件
	 */
	private BeanConfigurationParser beanConfigurationParser = new BeanConfigurationParser();

	/**
	 * 扫描根路径下的所有class文件
	 * 
	 * @param rootPath
	 *            扫描的根据经
	 * @return class文件的路径集合
	 */
	public List<String> getClassPathsByRootPath(String rootPath) {
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
	private void addBeanPathToList(File file, List<String> classPaths) {
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
	 * 根据扫描路径扫描Class并获取Class的全限定名
	 * 
	 * @param scanClassPath 扫描路径
	 * @return 全限定名集合
	 */
	public List<String> getQualifiedNames(String scanClassPath) {
		// 1. 实例化装载磁盘上classPath下的所有Class文件的全限定名
		List<String> qualifiedNames = new ArrayList<>();
		if (StringUtils.isNotEmpty(scanClassPath)) {
			// 2. 声明class-path路径
			String standardClassPath = convertedStandardFormatToFilePath(Constant.Path.CLASSPATH_UTF8);
			// 3. '.'转换为'/'
			String converterScanPath = convertClassPathToFilePath(scanClassPath);
			if (StringUtils.isNotEmpty(converterScanPath)) {
				// 4. 转换为UTF-8编码字符
				scanClassPath = Constant.Path.CLASSPATH_UTF8 + Constant.Path.convertClassPath(converterScanPath, Encoding.UTF8);
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
	public List<BeanDescription> scan(String scanClassPath) {

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

			// 5. load...
			// 加载配置
			List<BeanDescription> configBeans = beanConfigurationParser.parse(loadClass);
			if(null != configBeans) {
				beanDescriptions.addAll(configBeans);
			}

			// 加载BeanDescription
			beanDescriptions.add(beanDescriptionParser.parse(loadClass));
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
	private String convertClassPathToFilePath(String classPath) {
		if (StringUtils.isNotEmpty(classPath))
			return classPath.trim().replace(".", "/");
		return null;
	}
	
	/**
	 * 转换类路径的格式(. -> 系统文件分隔符)
	 * 
	 * @param path
	 *            转换路径
	 * @return 转换结果
	 */
	@SuppressWarnings("unused")
	private String convertClassPathToSystemFilePath(String classPath) {
		if (StringUtils.isNotEmpty(classPath))
			return classPath.trim().replace(".", SystemUtils.getSystemFileSeparator());
		return null;
	}

	/**
	 * 转换类路径的格式(\ -> .)
	 * 
	 * @param path
	 *            转换路径
	 * @return 转换结果
	 */
	private String convertedPathToFileStandardToClassStandard(String path) {
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
	private String convertedStandardFormatToFilePath(String filePath) {
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
	private String delClassPostfix(String qualifiedName) {
		if (StringUtils.isNotEmpty(qualifiedName)) {
			String substring = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
			if (StringUtils.isNotEmpty(substring))
				return substring;
		}
		return null;
	}

	public static void main(String[] args) {

		BeansScanner beansScanner = new BeansScanner();
		
		// 扫描路径
		String basePackage = "org.moon.framework.beans.test";

		// 扫描(注解类可以自定义传递,并不一定用@Component,可以自己实现一个类注解,但是根据传递路径扫描的类也只会是实现了此处传递的注解的类)
		List<BeanDescription> result = beansScanner.scan(basePackage);
		// 打印
		System.out.println(result);
	}
}