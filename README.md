# BingPic

每天定时获取[必应](https://cn.bing.com)的背景图片并上传到七牛

## 作者

木白

## 如何使用

1. 下载源码
2. 修改 `application.yml`
   - accessKey:		七牛的ACCESS_KEY
   - secretKey:		七牛的SECRET_KEY
   - bucketName:	七牛的存储空间
3. 使用maven构建运行

```
git clone https://github.com/ikyxxs/BingPic.git
cd BingPic
vim src/main/resources/application.yml
mvn clean install
mvn spring-boot:run
```

## 如何修改定时

修改 `ScheduledTasks.java` 里的`cron` 
