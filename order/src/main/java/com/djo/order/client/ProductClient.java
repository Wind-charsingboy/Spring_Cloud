package com.djo.order.client;

import com.djo.order.dataobject.ProductInfo;
import com.djo.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 这个类里面定义要调用的哪些接口
 * 这个注解里面的name是应用的名字 代表的是我要访问product里面的msg接口
 */
@FeignClient(name = "product", fallback = ProductClientFallback.class)
public interface ProductClient {

    //这个和server端相匹配是通过@GetMapping中的路径相匹配的 和方法名字没有任何关系
    //这个地方的地址就是要访问的server端的地址
    @GetMapping("/msg")
    String productMsg();

    /**
     * 调用商品服务的接口定义
     *
     * @param productIdList
     * @return 这个路径一定要填写完整
     */
    @PostMapping("/product/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);

    /**
     * 调用商品服务扣减库存
     *
     * @param cartDTOList
     */
    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}