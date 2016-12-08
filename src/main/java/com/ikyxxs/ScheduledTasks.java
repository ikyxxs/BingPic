package com.ikyxxs;

import com.ikyxxs.util.FileUtils;
import com.ikyxxs.util.HttpUtils;
import com.ikyxxs.util.QiniuUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
@Component
public class ScheduledTasks {

    @Value("${custom.url}")
    private String url;

    @Value("${custom.regex}")
    private String regex;

    @Scheduled(cron = "0 0 9 * * ?")        //每天9点执行
    public void reportCurrentTime() throws IOException {

        //获取bing.com网页内容
        String result = HttpUtils.get(url);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);

        //通过正则表达式获取网页内的图片链接
        if (matcher.find()) {
            String picUrl = matcher.group();
            String picName = FileUtils.getFileNameFromUrl(picUrl);

            if (!StringUtils.isEmpty(picName)) {
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
        }
    }
}