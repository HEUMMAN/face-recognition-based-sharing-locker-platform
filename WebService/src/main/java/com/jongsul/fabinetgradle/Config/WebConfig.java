package com.jongsul.fabinetgradle.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer interceptorConfigure(){
        System.out.println("로그인 인터셉터 호출 - 로그인페이지로 이동");
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AuthInterceptor())
                        .addPathPatterns("/board")
                        .addPathPatterns("/cabinet")
                        .addPathPatterns("/bill")
                        .addPathPatterns("/upload")
                        .addPathPatterns("/open")
                        .addPathPatterns("/mypage");
            }
        };
    }
}
