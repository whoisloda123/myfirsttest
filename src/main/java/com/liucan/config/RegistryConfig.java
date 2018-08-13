package com.liucan.config;

import com.liucan.common.serviceRegistry.ServiceRegistry;
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
    public ServiceRegistry serviceRegistry() {
        return new ServiceRegistry(servers);
    }
}
