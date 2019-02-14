package org.moon.framework.core.utils.basic;

import org.moon.framework.core.utils.basic.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by 明月   on 2019-01-13 / 23:16
 *
 * @email: 1814031271@qq.com
 *
 * @Description: 本地系统工具类
 */
public final class SystemUtils {

	private SystemUtils() {
	}

	// ====================Constant And Getter Method===========================

	/**
	 * 本地系统变量的常量集
	 */
	private static class SystemEnvironmentVariable {

		// 本地操作统的名称
		static final String SYSTEM_NAME = "os.name";

		// 本地操作统的架构
		static final String SYSTEM_ARCH = "os.arch";

		// 本地操作统的版本
		static final String SYSTEM_VERSION = "os.version";

		// 本地系统当前登录用户的账户名称
		static final String SYSTEM_USER_NAME = "user.name";

		// 本地系统当前登录用户的主目录
		static final String SYSTEM_USER_HOME = "user.home";

		// 本地系统当前登录用户的当前工作目录
		static final String SYSTEM_USER_DIR = "user.dir";

		// 本地系统的文件分隔符(UNIX: '/' | Window: '\')
		static final String SYSTEM_FILE_SEPARATOR = "file.separator";

		// 本地系统的路径分隔符(UNIX: ':' | Window: ';')
		static final String SYSTEM_PATH_SEPARATOR = "path.separator";

		// 本地系统的行分隔符(UNIX: /n | Window: \r\n)
		static final String SYSTEM_LINE_SEPARATOR = "line.separator";

	}

	/**
	 * 获取本地操作统的名称
	 */
	public static String getSystemName() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_NAME);
	}

	/**
	 * 获取本地操作统的架构
	 */
	public static String getSystemArch() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_ARCH);
	}

	/**
	 * 获取本地操作统的版本
	 */
	public static String getSystemVersion() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_VERSION);
	}

	/**
	 * 获取本地系统当前登录用户的账户名称
	 */
	public static String getSystemUserName() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_USER_NAME);
	}

	/**
	 * 获取本地系统当前登录用户的主目录
	 */
	public static String getSystemUserHome() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_USER_HOME);
	}

	/**
	 * 获取本地系统当前登录用户的当前工作目录
	 */
	public static String getSystemUserDir() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_USER_DIR);
	}

	/**
	 * 获取本地系统的文件分隔符(UNIX: '/' | Window: '\')
	 */
	public static String getSystemFileSeparator() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_FILE_SEPARATOR);
	}

	/**
	 * 获取本地系统的路径分隔符(UNIX: ':' | Window: ';')
	 */
	public static String getSystemPathSeparator() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_PATH_SEPARATOR);
	}

	/**
	 * 获取本地系统的行分隔符(UNIX: /n | Window: \r\n)
	 */
	public static String getSystemLineSeparator() {
		return System.getProperty(SystemEnvironmentVariable.SYSTEM_LINE_SEPARATOR);
	}

	/**
	 * Java的常量集
	 */
	private static class JavaEnvironmentVariable {

		// Java 运行时环境版本
		static final String JAVA_VERSION = "java.version";

		// Java 运行时环境供应商
		static final String JAVA_VENDOR = "java.vendor";

		// Java 运行时环境供应商的URL
		static final String JAVA_VENDOR_URL = "java.vendor";

		// Java 安装目录
		static final String JAVA_HOME = "java.home";

		// Java 运行时环境规范版本
		static final String JAVA_SPECIFICATION_VERSION = "java.specification.version";

		// Java 运行时环境规范供应商
		static final String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";

		// Java 运行时环境规范名称
		static final String JAVA_SPECIFICATION_NAME = "java.specification.name";

		// Java 类格式版本号
		static final String JAVA_CLASS_VERSION = "java.class.version";

		// Java 加载库时搜索的路径列表
		static final String JAVA_LIBRARY_PATH = "java.library.path";

		// Java 默认的临时文件路径
		static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

		// Java 要使用的JIT编译器的名称
		static final String JAVA_COMPILER = "java.compiler";

		// Java 一个或多个扩展目录的路径
		static final String JAVA_EXT_DIRS = "java.ext.dirs";

	}

	/**
	 * 获取Java 运行时环境版本
	 */
	public static String getJavaVersion() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_VERSION);
	}

	/**
	 * 获取Java 运行时环境供应商
	 */
	public static String getJavaVendor() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_VENDOR);
	}

	/**
	 * 获取Java 运行时环境供应商的URL
	 */
	public static String getJavaVendorUrl() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_VENDOR_URL);
	}

	/**
	 * 获取Java 安装目录
	 */
	public static String getJavaHome() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_HOME);
	}

	/**
	 * 获取Java 运行时环境规范版本
	 */
	public static String getJavaSpecificationVersion() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_SPECIFICATION_VERSION);
	}

	/**
	 * 获取Java 运行时环境规范供应商
	 */
	public static String getJavaSpecificationVendor() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_SPECIFICATION_VENDOR);
	}

	/**
	 * 获取Java 运行时环境规范名称
	 */
	public static String getJavaSpecificationName() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_SPECIFICATION_NAME);
	}

	/**
	 * 获取Java 类格式版本号
	 */
	public static String getJavaClassVersion() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_CLASS_VERSION);
	}

	/**
	 * 获取Java 加载库时搜索的路径列表
	 */
	public static String getJavaLibraryPath() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_LIBRARY_PATH);
	}

	/**
	 * 获取Java 加载库时搜索的路径列表
	 * 
	 * @param 建议使用SystemUtils.getSystemPathSeparator()获取本地系统的路径分隔符并作为参数.
	 * 
	 * @return 参数正确返回单个或多个路径,参数错误返回null
	 */
	public static String[] getJavaLibraryPath(String localSystemPathSeparator) {
		if (isSystemPathSeparator(localSystemPathSeparator))
			return System.getProperty(JavaEnvironmentVariable.JAVA_LIBRARY_PATH).split(localSystemPathSeparator);
		return null;
	}

	/**
	 * 获取Java 默认的临时文件路径
	 */
	public static String getJavaIoTmpdir() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_IO_TMPDIR);
	}

	/**
	 * 获取Java 要使用的JIT编译器的名称 
	 */
	public static String getJavaCompiler() {
		return System.getProperty(JavaEnvironmentVariable.JAVA_COMPILER);
	}

	/**
	 * 获取Java 一个或多个扩展目录的路径
	 * 
	 * @param 建议使用SystemUtils.getSystemPathSeparator()获取本地系统的路径分隔符并作为参数.
	 * 
	 * @return 参数正确返回单个或多个路径,参数错误返回null
	 */
	public static String[] getJavaExtDirs(String localSystemPathSeparator) {
		if (isSystemPathSeparator(localSystemPathSeparator))
			return System.getProperty(JavaEnvironmentVariable.JAVA_EXT_DIRS).split(localSystemPathSeparator);
		return null;
	}

	/**
	 * JVM的常量集
	 */
	private static class JVMEnvironmentVariable {

		// Java 虚拟机规范版本
		static final String JVM_SPECIFICATION_VERSION = "java.vm.specification.version";

		// Java 虚拟机规范供应商
		static final String JVM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";

		// Java 虚拟机规范供名称
		static final String JVM_SPECIFICATION_NAME = "java.vm.specification.name";

		// Java 虚拟机实现版本
		static final String JVM_VERSION = "java.vm.version";

		// Java 虚拟机实现供应商
		static final String JVM_VENDOR = "java.vm.vendor";

		// Java 虚拟机实现名称
		static final String JVM_NAME = "java.vm.name";

	}

	/**
	 * 获取Java 虚拟机规范版本
	 */
	public static String getJvmSpecificationVersion() {
		return System.getProperty(JVMEnvironmentVariable.JVM_SPECIFICATION_VERSION);
	}

	/**
	 * 获取Java 虚拟机规范供应商
	 */
	public static String getJvmSpecificationVendor() {
		return System.getProperty(JVMEnvironmentVariable.JVM_SPECIFICATION_VENDOR);
	}

	/**
	 * 获取Java 虚拟机规范供名称
	 */
	public static String getJvmSpecificationName() {
		return System.getProperty(JVMEnvironmentVariable.JVM_SPECIFICATION_NAME);
	}

	/**
	 * 获取Java 虚拟机实现版本
	 */
	public static String getJvmVersion() {
		return System.getProperty(JVMEnvironmentVariable.JVM_VERSION);
	}

	/**
	 * 获取Java 虚拟机实现供应商
	 */
	public static String getJvmVendor() {
		return System.getProperty(JVMEnvironmentVariable.JVM_VENDOR);
	}

	/**
	 * 获取Java 虚拟机实现名称
	 */
	public static String getJvmName() {
		return System.getProperty(JVMEnvironmentVariable.JVM_NAME);
	}

	// ======================Constant And Getter Method========================

	/**
	 * Window 系统名称前缀
	 */
	private static final String WINDOWS_SYSTEM_PREFIX = "win";
	/**
	 * Linux 系统名称前缀
	 */
	private static final String LINUX_SYSTEM_PREFIX = "lin";

	/**
	 * 打印单位
	 */
	public static enum PrintUnit {

		BYTE(0B1, "Byte"), KB(BYTE.size * 0B10000000000, "KB"), MB(KB.size * 0B10000000000,
				"MB"), GB(MB.size * 0B10000000000, "GB");

		private long size;
		private String unitName;

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}

		private PrintUnit(long size, String unitName) {
			this.size = size;
			this.unitName = unitName;
		}

	}

	/**
	 * 检测参数是否为本地系统的路径分隔符
	 */
	public static boolean isSystemPathSeparator(String localSystemPathSeparator) {
		if (StringUtils.isNotEmpty(localSystemPathSeparator)
				&& localSystemPathSeparator.equals(getSystemPathSeparator()))
			return true;
		return false;
	}

	/**
	 * 校验是否是Linux系统
	 */
	public static boolean isLinux() {
		return getSystemName().toLowerCase().startsWith(LINUX_SYSTEM_PREFIX);
	}

	/**
	 * 校验是否是Windows系统
	 */
	public static boolean isWindows() {
		return getSystemName().toLowerCase().startsWith(WINDOWS_SYSTEM_PREFIX);
	}

	/**
	 * 获取本地应用中的网卡的MAC地址,异常返回null
	 */
	public static String getMacAddress() {
		try {
			// 获取网卡，获取地址
			byte[] mac;
			mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// 二进制转为十六进制
				String str = Integer.toHexString(mac[i] & 0xff);
				// 美化格式
				if (str.length() == 1)
					sb.append("0" + str);
				else
					sb.append(str);
			}
			return sb.toString().toUpperCase();
		} catch (UnknownHostException e) {
			// 异常返回null
			return null;
		} catch (SocketException e) {
			// 异常返回null
			return null;
		}
	}

	/**
	 * 打印JVM内存信息,单位为空则返回null
	 */
	public static String getMemoryInfo(PrintUnit unit) {
		if (null != unit) {
			String unitName = unit.getUnitName();
			long min = Runtime.getRuntime().freeMemory() / unit.getSize();
			long max = Runtime.getRuntime().maxMemory() / unit.getSize();
			long free = Runtime.getRuntime().freeMemory() / unit.getSize();
			return "min -> " + min + unitName + ",\n"
					+ "max -> " + max + unitName + ",\n"
							+ "free -> " + free + unitName;
		}
		return null;
	}

	/**
	 * 打印JVM内存信息,单位为空则不打印
	 */
	public static void printMemory(PrintUnit unit) {
		if (null != unit)
			System.out.println(getMemoryInfo(unit));
	}

	public static void main(String[] args) {
//		System.out.println(PrintUnit.BYTE.getSize());
//		System.out.println(PrintUnit.KB.getSize());
//		System.out.println(PrintUnit.MB.getSize());
//		System.out.println(PrintUnit.GB.getSize());
		printMemory(PrintUnit.BYTE);
		System.out.println();
		printMemory(PrintUnit.KB);
		System.out.println();
		printMemory(PrintUnit.MB);
		System.out.println();
		printMemory(PrintUnit.GB);
	}
	// /**
	// * 执行命令行,异常返回null
	// */
	// public static String executeCommand(String[] command) {
	// BufferedReader input = null;
	// StringBuffer buffer = new StringBuffer();
	// try {
	// Process exec = Runtime.getRuntime().exec(command);
	//
	// input = new BufferedReader(new InputStreamReader(exec.getInputStream()));
	// String line;
	// while ((line = input.readLine()) != null) {
	// buffer.append(line);
	// }
	// return buffer.toString();
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// } finally {
	// try {
	// if (null != input) {
	// input.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	// public static void main(String[] args) {
	// // 本地系统环境变量
	// System.out.println("local system -> ");
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_NAME));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_ARCH));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_VERSION));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_USER_NAME));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_USER_HOME));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_USER_DIR));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_FILE_SEPARATOR));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_PATH_SEPARATOR));
	// System.out.println(System.getProperty(SystemEnvironmentVariable.SYSTEM_LINE_SEPARATOR));
	// System.out.println();
	// System.out.println();
	// // Java变量
	// System.out.println("java environment variable -> ");
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_VERSION));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_VENDOR));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_VENDOR_URL));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_HOME));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_SPECIFICATION_VERSION));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_SPECIFICATION_VENDOR));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_SPECIFICATION_NAME));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_CLASS_VERSION));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_LIBRARY_PATH));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_IO_TMPDIR));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_COMPILER));
	// System.out.println(System.getProperty(JavaEnvironmentVariable.JAVA_EXT_DIRS));
	// System.out.println();
	// System.out.println();
	// // JVM变量
	// System.out.println("jvm system -> ");
	// System.out.println(System.getProperty(JVMEnvironmentVariable.JVM_SPECIFICATION_VERSION));
	// System.out.println(System.getProperty(JVMEnvironmentVariable.JVM_SPECIFICATION_VENDOR));
	// System.out.println(System.getProperty(JVMEnvironmentVariable.JVM_SPECIFICATION_NAME));
	// System.out.println(System.getProperty(JVMEnvironmentVariable.JVM_VERSION));
	// System.out.println(System.getProperty(JVMEnvironmentVariable.JVM_VENDOR));
	// System.out.println(System.getProperty(JVMEnvironmentVariable.JVM_NAME));
	// }
}