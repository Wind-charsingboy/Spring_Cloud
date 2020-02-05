package com.djo.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //fallback是一个方法名 对应的就是一个方法
//    @HystrixCommand(fallbackMethod = "fallback")

    //对于上面那一种 如果有很多方法 就要写很多个fallback方法 就很麻烦 所以在该注解中使用的是默认的那个defaultFallback
//    @HystrixCommand

    //此处是设置超时的时间 超过这个时间就进行服务降级 如果不进行配置 默认的时间是1秒 所以当product中对应的接口设置休眠2秒
    //的时候 前端一般的访问时间在1.几秒的时候就会自动进行降级
    //这么自我设置之后 超时时间就会从1秒变为3秒 就会正常访问product的接口

    //这个配置可以拿到bootstrap中去 不过前面要加上hystrix.command.default 同时default是针对的所有的方法 也可以在配置文件中指定
    //某一个特定的方法对其进行配置
    //但将配置全部放到bootstrap.yml后 不要忘记添加@HystrixCommand注解
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })

    //服务熔断
    //此处的配置和上面的超时一样 是可以拿到配置文件中去的 不过前面要加上hystrix.command.default 如果想为特定的方法配置 只需将
    //default修改为特定的方法就可以了
    //但将配置全部放到bootstrap.yml后 不要忘记添加@HystrixCommand注解
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        //调用商品服务   地址 参数 返回值
        return restTemplate.postForObject("http://127.0.0.1:8082/product/listForOrder", Arrays.asList("222"), String.class);

//        上面的实现是在调用另一个服务的时候 抛出了异常 从而导致的服务降级 但是通过下面这种直接抛出异常 也是能造成降级的
//        所以自己的服务内部也可以进行降级
//        throw new RuntimeException("发生异常了");
    }

    //主逻辑中出故障了会调用的方法
    private String fallback() {
        return "太拥挤了 请稍后再试";
    }

    private String defaultFallback() {
        return "默认提示太拥挤了 请稍后再试";
    }
}
