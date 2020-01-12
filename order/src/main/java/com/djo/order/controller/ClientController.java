package com.djo.order.controller;

import com.djo.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientController {
    //springcloud提供的
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        //1.第一种方式 这种的缺点就是地址要写死 如果访问的url是个分布式的 这个就要考虑负载均衡的问题
        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject("http://localhost:8080/msg", String.class);


//        //2.第二种方式 这种可以解决写死路径的问题 但是每次都要去获取 再去拼接 也比较麻烦
//        //这个serviceId就是Eureka上面的Application的name 也就是配置文件中的spring.application.name
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        //根据这个ServiceInstance可以拿到host和port 所以可以构建地址
//        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
//        //getForObject中第二个参数是返回值的类型
//        String result = restTemplate.getForObject(url, String.class);

        //3.第三种方式 利用注解@LoadBalanced 可在路径中直接使用应用的名字
//        String result = restTemplate.getForObject("http://PRODUCT/msg", String.class);

        //4.第四种方式 使用feign
        String result = productClient.productMsg();

        log.info("result={}", result);
        return result;
    }




}
