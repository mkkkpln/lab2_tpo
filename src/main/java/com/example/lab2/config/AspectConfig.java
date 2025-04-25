package com.example.lab2.config;

import com.example.lab2.aop.FunctionInterceptorAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public FunctionInterceptorAspect functionInterceptorAspect() {
        return new FunctionInterceptorAspect();
    }
}
