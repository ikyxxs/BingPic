package com.ikyxxs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static String accessKey;     //七牛账号的ACCESS_KEY

    public static String secretKey;     //七牛账号的SECRET_KEY

    public static String bucketName;    //七牛账号的存储空间


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }

    @Value("${qiniu.accessKey}")
    public void setAccessKey(String accessKey) {
        Application.accessKey = accessKey;
    }

    @Value("${qiniu.secretKey}")
    public void setSecretKey(String secretKey) {
        Application.secretKey = secretKey;
    }

    @Value("${qiniu.bucketName}")
    public void setBucketName(String bucketName) {
        Application.bucketName = bucketName;
    }
}