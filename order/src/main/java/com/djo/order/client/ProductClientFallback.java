package com.djo.order.client;

import com.djo.order.dataobject.ProductInfo;
import com.djo.order.dto.CartDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public String productMsg() {
        return null;
    }

    //如果我们通过feign访问上面的listForOrder 产生了服务降级 就会访问到这里
    @Override
    public List<ProductInfo> listForOrder(List<String> productIdList) {
        return null;
    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {

    }
}
