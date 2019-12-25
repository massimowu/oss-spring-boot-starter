package cn.isuyu.boot.oss.starter.autoconfigure.service;

import cn.isuyu.boot.oss.starter.autoconfigure.properties.AliyunOssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Date;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/11/18 1:46 下午
 */
public class OssService {

    @Autowired
    private AliyunOssProperties ossProperties;

    /**
     * 上传文件的base64
     * @param objectName
     * @param base64
     */
    public String upload(String objectName,String base64) throws IOException {

        return upload(objectName,new BASE64Decoder().decodeBuffer(base64));

    }

    /**
     * 上传文件的byte[]
     * @param objectName
     * @param bytes
     */
    public String upload(String objectName,byte[] bytes) {
        OSS ossClient = null;
        try {
            ossClient = getClient();
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(ossProperties.getBucket(), objectName, new ByteArrayInputStream(bytes));

            ossClient.putObject(putObjectRequest);
            // 设置URL过期时间为100年，默认这里是int型，转换为long型即可
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
            // 生成URL
            return ossClient.generatePresignedUrl(ossProperties.getBucket(), objectName, expiration).toString().replaceFirst("http",ossProperties.getPrefix());
        } finally {
            ossClient.shutdown();
        }

    }

    /**
     * 上传本地文件
     * @param objectName
     * @param file
     */
    public String upload (String objectName, File file) {
        OSS ossClient = null;
        try {
            ossClient = getClient();

            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucket(), objectName, file);
            ossClient.putObject(putObjectRequest);

            // 设置URL过期时间为100年，默认这里是int型，转换为long型即可
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
            // 生成URL
            return ossClient.generatePresignedUrl(ossProperties.getBucket(), objectName, expiration).toString().replaceFirst("http",ossProperties.getPrefix());
        } finally {
            ossClient.shutdown();

        }
    }

    /**
     * 文件流上传
     * @param objectName
     * @param inputStream
     * @return
     */
    public String upload(String objectName, InputStream inputStream) {
        OSS ossClient = null;
        try {
            ossClient = getClient();

            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucket(), objectName, inputStream);
            ossClient.putObject(putObjectRequest);

            // 设置URL过期时间为100年，默认这里是int型，转换为long型即可
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
            // 生成URL
            return ossClient.generatePresignedUrl(ossProperties.getBucket(), objectName, expiration).toString().replaceFirst("http",ossProperties.getPrefix());

        } finally {
            ossClient.shutdown();

        }
    }

    /**
     * 文件下载
     * @param objectName
     * @return
     * @throws IOException
     */
    public BufferedReader download(String objectName) throws IOException {
        OSS ossClient = null;
        BufferedReader reader = null;
        try {
            ossClient = getClient();

            OSSObject ossObject = ossClient.getObject(ossProperties.getBucket(),objectName);
            reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

            }
            reader.close();
            return reader;
        } finally {
            ossClient.shutdown();

        }
    }

    /**
     * 文件下载
     * @param objectName
     * @param file
     */
    public void downLoad(String objectName,File file) {
        OSS ossClient = null;

        try {
            ossClient = getClient();
            ossClient.getObject(new GetObjectRequest(ossProperties.getBucket(), objectName), file);
        } finally {
            ossClient.shutdown();

        }
    }

    public boolean exist(String objectName) {
        OSS ossClient = null;

        try {
            ossClient = getClient();
            return ossClient.doesObjectExist(ossProperties.getBucket(), objectName);
        } finally {
            ossClient.shutdown();

        }
    }

    /**
     * 文件删除
     * @param objectName
     */
    public void delete(String objectName) {
        OSS ossClient = null;

        try {
            ossClient = getClient();
            ossClient.deleteObject(ossProperties.getBucket(), objectName);
        } finally {
            ossClient.shutdown();

        }
    }

    /**
     * 获取oss操作对象
     * @return
     */
    private OSS getClient() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getKey(), ossProperties.getSecret());
    }
}
