package cn.isuyu.boot.oss.starter.autoconfigure.autoconfig;

import cn.isuyu.boot.oss.starter.autoconfigure.properties.AliyunOssProperties;
import cn.isuyu.boot.oss.starter.autoconfigure.service.OssService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/11/18 11:09 上午
 */
@Configuration
@ConditionalOnClass(OssService.class)
@EnableConfigurationProperties(AliyunOssProperties.class)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OssService ossService () {
        return new OssService();
    }
}
