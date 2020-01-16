package com.djo.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收mq消息
 */
@Slf4j
@Component
public class MqReceive {
//  1.@RabbitListener(queues = "myQueue")
//  2.自动创建队列@RabbitListener(queuesToDeclare = @Queue("myQueue"))
//  3.自动创建exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message) {
        log.info("mqReceiver: {}", message);
    }


    /**
     * 数码供应商服务 接收信息
     * 这个key就代表着只接受数码类型的消息 key就是用来分组的 routingKey就是在发送的时候区分往哪个队列转发
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message) {
        log.info("computer mqReceiver: {}", message);
    }


    /**
     * 水果供应商服务 接收信息
     * 这个key就代表着只接受水果类型的消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message) {
        log.info("fruit mqReceiver: {}", message);
    }
}
