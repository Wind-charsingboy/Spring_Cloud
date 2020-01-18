package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置
 * C - Cross O - Origin R - Resource S - Share  就是跨域资源共享
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        //是否支持cookie跨域
        config.setAllowCredentials(true);
        //放哪些原始域 *就代表所有域名 如果具体化 就是类似于http://www.a.com
        config.setAllowedOrigins(Arrays.asList("*"));
        //允许的头
        config.setAllowedHeaders(Arrays.asList("*"));
        //允许的方法  "GET"   "POST"
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存的时间 在相同的时间段里面 就不会对相同的跨域请求进行检查了
        config.setMaxAge(300L);

        //把跨域的配置注册到source上面去
        //第一个参数是配置的路径 此处是所有
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
