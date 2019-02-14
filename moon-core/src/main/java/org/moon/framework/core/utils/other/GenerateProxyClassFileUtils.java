package org.moon.framework.core.utils.other;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 明月   on 2018-09-21 / 11:54
 *
 * @Description: 将持久代中的动态代理对象的源代码实现保存至本地目录
 */
@SuppressWarnings("restriction")
public class GenerateProxyClassFileUtils {

	/**
	 * Save proxy class to path
	 * 
	 * @param path path to save proxy class
	 * @param proxyClassName name of proxy class
	 * @param interfaces interfaces of proxy class
	 */
	public static boolean saveProxyClass(String path, String proxyClassName, Class<?>[] interfaces) {

		if (proxyClassName == null || path == null) {
			return false;
		}

		byte[] classFile = ProxyGenerator.generateProxyClass(proxyClassName, interfaces);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			out.write(classFile);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	interface UserServiceImpl {
		int add();

		int delete(int id);
	}

	// 测试
	public static void main(String[] args) {
		// 参数一：必须是一个class文件
		// 参数二：生成的代理类的名称
		// 参数三：需要实现的接口
		GenerateProxyClassFileUtils.saveProxyClass("e:\\\\UserServiceImplProxy.class", "UserServiceImplProxy5",
				new Class[] { UserServiceImpl.class });
	}
}