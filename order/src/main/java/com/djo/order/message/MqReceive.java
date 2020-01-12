package com.djo.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收mq消息
 */
@Slf4j
@Component
public class MqReceive {
//  1.@RabbitListener(queues = "myQueue")
//  2.自动创建队列
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void process(String message) {
        log.info("mqReceiver: {}", message);
    }
}
