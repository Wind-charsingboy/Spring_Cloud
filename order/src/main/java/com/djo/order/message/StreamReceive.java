package com.djo.order.message;

import com.djo.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 定义一个接收端
 */
@Component
//1.添加注解 里面写的就是之前定义好的接口
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceive {

    //2.进行监听
//    @StreamListener("myMessage")
//    public void process(Object message){
//        log.info("StreamReceive:{}", message);
//    }

    /**
     * 接收orderDTO对象信息
     * @param message
     */
    @StreamListener("myMessage")
    //处理完之后要把消息发送到myMessage2
    @SendTo("myMessage2")
    public String process(OrderDTO message){
        log.info("StreamReceive:{}", message);
        //发送mq消息 就是把"receive success"这个消息往@SendTo的没有myMessage2里面去发送 谁来监听myMessage2 谁就会收到
        return "receive success";
    }

    /**
     * 用来接收上面处理成功之后的 作为一个消费者
     * @param message
     */
    @StreamListener("myMessage2")
    public void process2(String message){
        log.info("StreamReceive2:{}", message);
    }
}
