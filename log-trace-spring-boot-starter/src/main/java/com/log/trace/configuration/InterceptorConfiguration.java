package com.log.trace.configuration;

import com.log.trace.global.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 10:38
 * @version: 1.0
 */
@Slf4j
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
        log.info("【TraceLog】配置LogInterceptor成功");
    }
}
