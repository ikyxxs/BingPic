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
    private static final Pattern IMG_PATTERN = Pattern.compile("/th\\?(.*?)\\.jpg");

    /**
     * 每天9点获取背景图链接并上传至七牛云
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void execute() throws IOException {

        // 获取bing.com网页内容
        String result = HttpUtils.get(BING_URL);

        // 通过正则表达式获取网页内的图片链接
        Matcher matcher = IMG_PATTERN.matcher(result);
        while (matcher.find()) {
            String picUrl = BING_URL + matcher.group();
            if (picUrl.contains("_tmb.")) {
                continue;
            }
            String picName = FileUtils.getFileNameFromUrl(picUrl);
            if (!StringUtils.isEmpty(picName)) {
                picName = picName.substring(picName.indexOf('=') + 1);
                //下载图片到本地
                HttpUtils.downloadPicture(picUrl, picName);

                File file = new File(picName);
                if (file.exists()) {
                    //上传图片到七牛
                    QiniuUtils.upload(file.getPath(), picName);
                    log.info(picName);
                    //删除本地的图片
                    file.deleteOnExit();
                }
            }
            break;
        }
    }
}
