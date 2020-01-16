package com.djo.order.controller;

import com.djo.order.dto.OrderDTO;
import com.djo.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 定义的是一个发送方
 */
@RestController
public class SendMessageController {
    @Autowired
    private StreamClient streamClient;

    /**
     * 传递String类型的消息
     */
    @GetMapping("/sendMessage")
    public void process() {
        String sendMessage = "now" + new Date();
        //这个参数是个Message对象 此处使用MessageBuild
        streamClient.output().send(MessageBuilder.withPayload(sendMessage).build());
    }

    /**
     * 传递orderDTO对象
     */
    @GetMapping("/sendOrderMessage")
    public void processOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        //这个参数是个Message对象 此处使用MessageBuild
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
