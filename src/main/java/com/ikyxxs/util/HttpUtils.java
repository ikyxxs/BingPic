package com.ikyxxs.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * HTTP工具类
 *
 * @author mubai
 * @date 2016/12/08
 */
public class HttpUtils {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    /**
     * 通过HTTP GET方式获取网页内容
     *
     * @param url 链接
     * @return 网页内容
     * @throws IOException IO异常
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = CLIENT.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 下载图片
     *
     * @param picUrl  图片链接
     * @param picName 图片地址
     */
    public static void downloadPicture(String picUrl, String picName) {
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            URL url = new URL(picUrl);
            dataInputStream = new DataInputStream(url.openStream());

            fileOutputStream = new FileOutputStream(new File(picName));

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
