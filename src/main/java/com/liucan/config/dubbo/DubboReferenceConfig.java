package com.liucan.config.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.liucan.common.dubbo.IBootDubboService;
import org.springframework.context.annotation.Bean;

/**
 * @author liucan
 * @date 2018/8/5
 * @brief 消费者配置类
 */
//@Configuration
public class DubboReferenceConfig extends DubboBaseConfig {
    @Bean
    public ReferenceBean<IBootDubboService> bootDubboServiceReferenceBean() {
        ReferenceBean<IBootDubboService> ref = new ReferenceBean<>();
        ref.setTimeout(5000);
        ref.setRetries(3);
        ref.setVersion("1.1.0");
        ref.setCheck(false);
        ref.setInterface(IBootDubboService.class);
        return ref;
    }

    @Bean("dubbo-consumer")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-consumer");
        return applicationConfig;
    }
}
