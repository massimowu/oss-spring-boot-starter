package cn.isuyu.boot.oss.starter.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/11/18 11:19 上午
 */
@Data
@ConfigurationProperties(prefix = AliyunOssProperties.ALIYUN_OSS_PREFIX )
public class AliyunOssProperties {

    public static final String ALIYUN_OSS_PREFIX = "aliyun.oss";

    /**
     * endpoint
     */
    private String endpoint;

    /**
     * bucket
     */
    private String bucket;

    /**
     * accessKeyId
     */
    private String key;

    /**
     * accessKeySecret
     */
    private String secret;

    /**
     * 前缀
     */
    private String prefix = "http";
}
