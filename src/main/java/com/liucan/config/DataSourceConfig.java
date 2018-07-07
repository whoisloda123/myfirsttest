package com.liucan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author liucan
 * @date 2018/7/1
 * @brief mysql配置
 *        1.单个属性绑定：@Value("${mysql.driver}")
 *        2.整个类属性绑定：@ConfigurationProperties
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mysql")
@PropertySource(value = "classpath:properties/mysql.properties")
public class DataSourceConfig {
    private String driver;
    private String url;
    private String username;
    private String password;

    /**
     * mysql的dataSource
     */
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
}
