package org.moon.framework.core.utils.file;

import org.moon.framework.core.utils.basic.StringUtils;

/**
 * Created by 明月 on 2019-01-25 / 17:11
 *
 * @email: 1814031271@qq.com
 * @Description: 文件操作工具类
 */
public final class FileUtils {
    
    private FileUtils() {
        
    }

    /**
     * 删除文件后缀名
     *
     * @param fileName
     *            文件名称
     * @return 删除后的结果
     */
    public static String deleteClassSuffix(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            String substring = fileName.substring(0, fileName.lastIndexOf("."));
            if (StringUtils.isNotEmpty(substring))
                return substring;
        }
        return null;
    }
    
}
