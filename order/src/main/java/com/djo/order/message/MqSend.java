package com.djo.order.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Component
@RestController
@RequestMapping("/test")
public class MqSend {
    /**
     * 用于发送mq
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 第一个参数是自己定义的队列的名字 第二个参数是要发送的消息
     */
    @GetMapping("/send")
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }
}
