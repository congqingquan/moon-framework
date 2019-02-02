package org.moon.framework.beans.util;

import org.moon.framework.core.constant.Constant;
import org.moon.framework.core.utils.FileUtils;
import org.moon.framework.core.utils.StringUtils;
import org.moon.framework.core.utils.SystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 明月 on 2019-01-25 / 15:39
 *
 * @email: 1814031271@qq.com
 * @Description: 类路径溶解器
 */
public class ClassPathDissolver {

    /**
     * 根据扫描路径扫描路径下所有Class文件的全限定名
     *
     * @param startupClassQualifiedNamePath 扫描路径（程序启动类的全限定名格式路径）
     * @return 全限定名集合
     */
    public static List<String> getQualifiedNamesInClassPath(String startupClassQualifiedNamePath) {
        // 1. 实例化装载磁盘上classpath下的所有Class文件的全限定名
        List<String> qualifiedNames = new ArrayList<>();
        if (StringUtils.isNotEmpty(startupClassQualifiedNamePath)) {
            // 2. 获取启动类在磁盘中的绝对路径
            startupClassQualifiedNamePath = getAbsolutePathByQualifiedNamePath(startupClassQualifiedNamePath);
            // 3. 获取启动类所在目录下的所有类文件的绝对路径
            List<String> classFileAbsolutePaths = getClassPathsByClassQualifiedNamePath(startupClassQualifiedNamePath);
            String next;
            // 4. 解析扫描到的类文件的全限定名格式路径
            for (Iterator<String> iterator = classFileAbsolutePaths.iterator(); iterator.hasNext(); ) {
                qualifiedNames.add(getQualifiedNameByClassFileAbsolutePath(iterator.next()));
            }
        }
        return qualifiedNames;
    }

    /**
     * 扫描根路径下的所有class文件的路径
     *
     * @param classQualifiedNamePath 扫描的根据经
     * @return class文件的路径集合
     */
    public static List<String> getClassPathsByClassQualifiedNamePath(String classQualifiedNamePath) {
        List<String> classesPath = null;
        if (StringUtils.isNotEmpty(classQualifiedNamePath)) {
            classesPath = new ArrayList<>();
            File[] listFiles = new File(classQualifiedNamePath).listFiles();
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
     * @param file       文件实例
     * @param classPaths 存储集合
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
     * 根据类的全限定名
     *
     * @param qualifiedNamePath 全限定名路径
     * @return 类的绝对路径
     */
    public static String getAbsolutePathByQualifiedNamePath(String qualifiedNamePath) {
        String absolutePath = Constant.Path.CLASSPATH + qualifiedNamePath.replace(".", "/");
        return absolutePath.substring(0, absolutePath.lastIndexOf("/"));
    }

    /**
     * 根据类文件的绝对路径获取类的全限定名格式路径
     *
     * @param classFileAbsolutePath Class文件的绝对路径
     * @return Class文件的全限定名格式路径
     */
    public static String getQualifiedNameByClassFileAbsolutePath(String classFileAbsolutePath) {
        String afterSubstring = classFileAbsolutePath.substring(classFileAbsolutePath.lastIndexOf(Constant.Path.CLASSPATH)
                + Constant.Path.CLASSPATH.length());

        afterSubstring = afterSubstring.replace(SystemUtils.getSystemFileSeparator(), ".");

        return FileUtils.deleteClassSuffix(afterSubstring);
    }
}