package com.liucan.boot.framework.config.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.liucan.boot.service.dubbo.BootDubboServiceImpl;
import com.liucan.boot.service.dubbo.IBootDubboService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liucan
 * @date 2018/8/5
 * @brief dubbo生产者配置
 */
@Configuration
public class DubboServiceConfig extends DubboBaseConfig {
    //@Bean
    public ServiceBean<IBootDubboService> bootDubboServiceServiceExport(BootDubboServiceImpl bootDubboService) {
        ServiceBean<IBootDubboService> serviceBean = new ServiceBean<>();
        serviceBean.setTimeout(5000);
        serviceBean.setRetries(3);
        serviceBean.setVersion("1.1.0");
        serviceBean.setProxy("javassist");
        serviceBean.setRef(bootDubboService);
        serviceBean.setInterface(IBootDubboService.class.getName());
        return serviceBean;
    }

    @Bean("dubbo-provider")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-provider");
        return applicationConfig;
    }
}
