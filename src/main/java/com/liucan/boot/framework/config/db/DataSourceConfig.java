package com.liucan.boot.framework.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 一：单数据源配置
 * mybatis配置，pom.xml里面添加mybatis-spring-boot-starter依赖自动将会提供如下，而不用手动自己创建：
 * 1.自动检测现有的DataSource
 * 2.将创建并注册SqlSessionFactory的实例，该实例使用SqlSessionFactoryBean将该DataSource作为输入进行传递
 * 3.将创建并注册从SqlSessionFactory中获取的SqlSessionTemplate的实例。
 * 4.自动扫描您的mappers，将它们链接到SqlSessionTemplate并将其注册到Spring上下文，以便将它们注入到您的bean中。
 * 就是说，使用了该Starter之后，只需要定义一个DataSource即可（application.properties中可配置），
 * 它会自动创建使用该DataSource的SqlSessionFactoryBean以及SqlSessionTemplate。会自动扫描你的Mappers，
 * 连接到SqlSessionTemplate，并注册到Spring上下文中
 * 5.在Spring Boot中，当我们使用了spring-boot-starter-jdbc或spring-boot-starter-data-jpa依赖的时候，
 * 框架会自动默认将dataSource分别注入DataSourceTransactionManager或JpaTransactionManager，而我们不需要手动添加
 * transactionManager的事务管理bean
 * <p>
 * 二：多数据源配置
 * mysql多数据源配置, 多数据源配置要求必须有一个是主, 用@Primary注解
 *
 * @author liucan
 * @version 2018/7/1
 */
@Data
@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "java-learn")
@PropertySource("classpath:properties/db.properties")
@MapperScan(basePackages = "com.liucan.boot.persist.mybatis.mapper")
public class DataSourceConfig {
    private String driver;
    private String url;
    private String userName;
    private String password;

    @Bean(destroyMethod = "close")
    //@Primary
    public DruidDataSource javaLearnDataSource(DruidConfig druidConfig) {
        DruidDataSource druidDataSource = druidConfig.dataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driver);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory javaLearnSqlSessionFactory(@Qualifier("javaLearnDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager javaLearnTransactionManager(@Qualifier("javaLearnDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DSLContext javaLearnDSL(@Qualifier("javaLearnDataSource") DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }
}
