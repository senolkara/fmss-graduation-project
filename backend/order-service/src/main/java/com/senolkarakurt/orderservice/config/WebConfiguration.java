package com.senolkarakurt.orderservice.config;

import com.senolkarakurt.orderservice.component.BearerTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerTokenInterceptor());
    }

    @Bean
    public BearerTokenInterceptor bearerTokenInterceptor() {
        return new BearerTokenInterceptor();
    }
}
