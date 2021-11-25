package com.ikyxxs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具类
 *
 * @author mubai
 * @date 2016/12/08
 */
public class FileUtils {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("[^/\\\\\\\\]+$");

    /**
     * 根据链接获取文件名
     *
     * @param url 链接
     * @return 文件名
     */
    public static String getFileNameFromUrl(String url) {
        Matcher matcher = FILE_NAME_PATTERN.matcher(url);
        return matcher.find() ? matcher.group() : "";
    }
}
