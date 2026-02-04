package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 配置类，用于创建AliOssUtil对象
 */
@Slf4j
@Configuration//标识这是一个配置类。Spring 容器在启动时会扫描带有此注解的类
public class OssConfiguration {

    @Bean//告诉 Spring 容器：“执行这个方法，并把返回的对象放进容器里管理”
    @ConditionalOnMissingBean//“只有当容器中不存在该类型的 Bean 时，才创建这个 Bean”
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传客户端：{}", aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
