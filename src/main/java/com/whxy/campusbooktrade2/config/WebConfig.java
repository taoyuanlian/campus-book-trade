package com.whxy.campusbooktrade2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    // 只留登录拦截器，去掉 AdminInterceptor！！！
    // 因为你的 AdminController 自己已经做了权限判断，不需要再拦截一遍！

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/book/**", "/order/**", "/admin/**")
                .excludePathPatterns("/user/login", "/user/register", "/book/list");
    }
}