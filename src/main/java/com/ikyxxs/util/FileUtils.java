package com.ikyxxs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mubai on 16/12/8.
 */
public class FileUtils {

    /**
     * 根据链接获取文件名
     *
     * @param url 链接
     * @return 文件名
     */
    public static String getFileNameFromUrl(String url) {
        Pattern pattern = Pattern.compile("[^/\\\\\\\\]+$");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }
}
