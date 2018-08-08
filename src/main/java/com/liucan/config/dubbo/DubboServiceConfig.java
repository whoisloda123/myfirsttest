package com.liucan.config.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.liucan.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liucan
 * @date 2018/8/5
 * @brief dubbo生产者配置
 */
@Configuration
public class DubboServiceConfig extends DubboBaseConfig {
    @Bean
    public ServiceBean<Person> personServiceExport(Person person) {
        ServiceBean<Person> serviceBean = new ServiceBean<>();
        serviceBean.setTimeout(5000);
        serviceBean.setRetries(3);
        serviceBean.setVersion("1.1.0");
        serviceBean.setProxy("javassist");
        serviceBean.setRef(person);
        serviceBean.setInterface(Person.class.getName());
        return serviceBean;
    }

    @Bean("dubbo-provider")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-provider");
        return applicationConfig;
    }
}
