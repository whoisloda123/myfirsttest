package com.liucan.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liucan
 * @date 2018/7/15
 * @brief pom.xml里面添加mybatis-spring-boot-starter依赖自动将会提供如下，而不用手动自己创建：
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
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.liucan.mybatis.dao")
public class TransactionManagementConfig {
}
