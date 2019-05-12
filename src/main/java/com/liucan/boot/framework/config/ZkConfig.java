package com.liucan.boot.framework.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:properties/zk.properties")
public class ZkConfig {
    private String servers;
}
