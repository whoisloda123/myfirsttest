package com.liucan.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
     * 1.destroy-method="close"的作用是在spring将该bean移出ioc容器是调用，
     *   当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
     * 2.Spring Boot会智能地选择我们自己配置的这个DataSource实例
     */
    @Bean(destroyMethod = "close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        dataSource.setInitialSize(2); //初始化时建立物理连接的个数
        dataSource.setMaxActive(20); //最大连接池数量
        dataSource.setMinIdle(0); //最小连接池数量
        dataSource.setMaxWait(60000); //获取连接时最大等待时间，单位毫秒。
        dataSource.setValidationQuery("SELECT 1"); //用来检测连接是否有效的sql
        dataSource.setTestOnBorrow(false); //申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(true); //建议配置为true，不影响性能，并且保证安全性。
        dataSource.setPoolPreparedStatements(false); //是否缓存preparedStatement，也就是PSCache
        return dataSource;
    }
}
