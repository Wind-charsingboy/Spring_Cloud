package com.djo.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

    @Bean
    //这个注解和第二种方式中的LoadBalancerClient是一样的
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
