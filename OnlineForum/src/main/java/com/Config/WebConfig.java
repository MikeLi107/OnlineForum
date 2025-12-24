package com.Config;

import com.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // 拦截所有
                // ↓↓↓ 必须放行静态资源和登录相关路径 ↓↓↓
                .excludePathPatterns("/", "/login", "/captcha", "/css/**", "/js/**", "/images/**");
    }
}