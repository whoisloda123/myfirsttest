package com.liucan.boot.framework.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author liucan
 * @version 19-5-30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "amazon")
@PropertySource("classpath:properties/db.properties")
public class DBAmazonDataConfig {
    private String driver;
    private String url;
    private String userName;
    private String password;

    @Bean(destroyMethod = "close")
    //@Primary
    public DruidDataSource amazonDataSource(DruidConfig druidConfig) {
        DruidDataSource druidDataSource = druidConfig.dataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driver);
        return druidDataSource;
    }

    @Bean
    public DSLContext amazonDSL(@Qualifier("amazonDataSource") DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }
}
