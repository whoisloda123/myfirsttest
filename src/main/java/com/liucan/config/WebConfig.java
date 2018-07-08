package com.liucan.config;

import com.liucan.common.interceptor.CommonInterceptor;
import com.liucan.common.validtor.CommonValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author liucan
 * @date 2018/7/1
 * @brief sprig-mvc默认配置的定制化，本身有默认配置
 * WebMvcConfigurerAdapter，spring boot 2.0以后过时，用WebMvcConfigurer代替
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 配置静态访问资源,自定义静态资源映射目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
    }

    /**
     * 转换与格式化
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    /**
     * 验证器
     */
    @Override
    public Validator getValidator() {
        return new CommonValidator();
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/bootlearn/**").excludePathPatterns("/bootlearn/login");
    }

    /**
     * 视图控制器
     * 立即将一个请求转发（forwards）给一个视图
     * 当控制器除了将视图渲染到响应中外不需要执行任何逻辑时使用
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //直接设置登录页面
        registry.addViewController("/bootlearn/login").setViewName("login");
    }

    /**
     * 视图解析器
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
    }

    /**
     * 路径匹配配置
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    }

    /**
     * 消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }
}
