package com.liucan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: liucan
 * @Date: 2018/7/6
 * @Description: @SpringBootApplication包括下面：
 *               1.@SpringBootApplication：和@Configuration一样
 *               2.@EnableAutoConfiguration：
 *                 a.根据添加的jar包来配置项目的默认配置，比如根据spring-boot-starter-web ，
 *                   来判断你的项目是否需要添加了webmvc和tomcat，就会自动的帮你配置web项目中所需要的默认配置
 *                 b.可以自动配置自己的jar包等等---后续可以看一下？
 *               3.@ComponentScan：扫描当前包及其子包
 *               4.所有一般将该类放在最顶层，方便其子包扫描到，也可以@SpringBootApplication(scanBasePackageClasses = MyConfig.class)
 */

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
