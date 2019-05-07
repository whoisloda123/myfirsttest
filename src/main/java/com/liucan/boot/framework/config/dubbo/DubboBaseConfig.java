package com.liucan.boot.framework.config.dubbo;

import com.alibaba.dubbo.config.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liucan
 * @date 2018/8/5
 * @brief
 */
@Data
@Configuration
public class DubboBaseConfig {
    @Value("${spring.dubbo.registry.address}")
    private String address;

    @Bean("dubboRegistryConfig")
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(address);
        registryConfig.setProtocol("curator");
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

    @Bean
    public MonitorConfig monitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }


    @Bean
    public ReferenceConfig referenceConfig() {
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setMonitor(monitorConfig());
        return referenceConfig;
    }

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setMonitor(monitorConfig());
        return providerConfig;
    }
}
