package com.ikyxxs;

import com.ikyxxs.util.FileUtils;
import com.ikyxxs.util.HttpUtils;
import com.ikyxxs.util.QiniuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时任务
 *
 * @author mubai
 * @date 2016/12/08
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * Bing网址
     */
    private static final String BING_URL = "https://cn.bing.com";

    /**
     * 背景图正则表达式
     */
    private static final Pattern IMG_PATTERN = Pattern.compile("\"Wallpaper\":\"([^\"]*)");

    /**
     * 每天9点获取背景图链接并上传至七牛云
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void execute() throws IOException {
        String picUrl = extractImgUrl();
        if (StringUtils.isEmpty(picUrl)) {
            return;
        }
        String picName = FileUtils.getFileNameFromUrl(picUrl);
        if (StringUtils.isEmpty(picName)) {
            return;
        }
        picName = picName.substring(picName.indexOf('=') + 1);

        //下载图片到本地
        HttpUtils.downloadPicture(picUrl, picName);

        File file = new File(picName);
        if (file.exists()) {
            //上传图片到七牛
            QiniuUtils.upload(file.getPath(), picName);
            log.info(picName);
            //删除本地的图片
            file.delete();
        }
    }

    /**
     * 提取图片链接
     */
    public String extractImgUrl() {
        try {
            String text = HttpUtils.get(BING_URL);
            Matcher m = IMG_PATTERN.matcher(text);
            if (m.find()) {
                String wallpaperLink = m.group(1);
                if (wallpaperLink.contains("jpg\\u")) {
                    wallpaperLink = wallpaperLink.substring(0, wallpaperLink.indexOf("\\u"));
                }
                return BING_URL + wallpaperLink;
            }
        } catch (Exception e) {
            log.error("提取图片链接异常", e);
        }
        return null;
    }
}
