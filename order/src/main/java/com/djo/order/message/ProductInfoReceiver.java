package com.djo.order.message;

import com.djo.order.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Slf4j
@Transactional
public class ProductInfoReceiver {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(List<ProductInfo> productInfoList) {
        log.info("从{}接收到消息:{}", "productInfo", productInfoList);

        //接收到消息后 存储到redis中
        //此处的222对应的是id 7对应的是库存数
        //对于上面的返回是个list的时候 就要for循环去单个去存储
        redisTemplate.opsForValue().set(String.format("product_stock_%s", "222"), "7");
    }
}
