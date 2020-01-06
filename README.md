# 阿里云对象存储OSS二次封装的spring-boot-starter


#### 使用方法
**1.** pom文件直接添加依赖(该jar已经发布到maven中央仓库)
```xml
<dependency>
    <groupId>cn.isuyu.boot</groupId>
    <artifactId>oss-spring-boot-starter</artifactId>
    <version>1.0.3</version>
</dependency>
```

**2.** 在配置文件添加下面的配置
```yaml
aliyun:
  oss:
    endpoint: 
    key: 
    secret: 
    bucket: 
    #配置上传返回文件地址的协议
    #不配置返回默认(http) http://huluwa-ec.oss-accelerate.aliyuncs.com/test.jpg
    #配置https返回 https://huluwa-ec.oss-accelerate.aliyuncs.com/test.jpg
    prefix: https
```

**3.** 代码中的使用

- 依赖注入OssService对象

```java
    @Autowired
    private OssService ossService;

```

- 文件上传

```java
//文件base64上传
String path = ossService.upload("test.jpg",base64);
//path = https://huluwa-ec.oss-accelerate.aliyuncs.com/test.jpg

//本地文件上传 
String path = ossService.upload("test2.jpg",new File("../data/1.jpg"));

//byte[]数组上传
String path = ossService.upload("test3.jpg",byte[] bytes);

//文件流上传
String path = ossService.upload("test4.jpg",new FileInputStream(new File("../data/1.jpg")));

```

- 文件下载

```java
//下载返回BufferedReader
BufferedReader bufferedReader = ossService.download("test.jpg");

//下载到本地
ossService.downLoad("test4.jpg",new File("../data/t.jpg"));
```

- 判断文件是否存在

```java
boolean flag = ossService.exist("test.jpg");
```

- 文件删除

```java
ossService.delete("test4.jpg");
```

