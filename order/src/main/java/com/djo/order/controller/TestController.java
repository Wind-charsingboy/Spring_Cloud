package com.djo.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

    @Value("${env}")
    private String env;

    @Value("${spring.cloud.bus.id}")
    private String id;

    @GetMapping("/order")
    public String test() {
        System.out.println(env);
        System.out.println(id);
        return id;
    }
}
