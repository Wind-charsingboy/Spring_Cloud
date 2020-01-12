package com.djo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//这个注解最好加上扫描的包的名字 因为可能写在其他的项目中 否则扫描不到 如果写在product项目中 就像下面这样配置
//@EnableFeignClients(basePackages = "com.djo.product.client")
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
