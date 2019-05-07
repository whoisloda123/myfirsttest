package com.liucan.boot.framework.config;

import com.liucan.boot.service.zk.ZkServiceRegistry;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liucan
 * @date 2018/7/24
 * @brief 服务注册
 */
@Data
@Configuration("serverRegistryConfig")
public class RegistryConfig {
    @Value("${registry.servers}")
    private String servers;

    @Bean
    public ZkServiceRegistry serviceRegistry() {
        return new ZkServiceRegistry(servers);
    }
}
