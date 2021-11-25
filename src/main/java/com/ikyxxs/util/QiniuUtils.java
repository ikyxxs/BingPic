package com.ikyxxs.util;

import com.ikyxxs.Application;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 七牛工具类
 *
 * @author mubai
 * @date 2016/12/08
 */
public class QiniuUtils {

    private static final Logger log = LoggerFactory.getLogger(QiniuUtils.class);

    //密钥配置
    private static Auth auth = Auth.create(Application.accessKey, Application.secretKey);

    //自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    private static Zone z = Zone.autoZone();
    private static Configuration c = new Configuration(z);

    //创建上传对象
    private static UploadManager uploadManager = new UploadManager(c);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private static String getUpToken() {
        return auth.uploadToken(Application.bucketName);
    }

    /**
     * 上传文件到七牛
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @throws IOException
     */
    public static void upload(String filePath, String fileName) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, fileName, getUpToken());

            //打印返回的信息
//            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            log.error("", e);
        }
    }
}

