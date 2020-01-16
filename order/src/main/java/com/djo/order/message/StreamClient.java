package com.djo.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {
    //此处的Input就是对应于SubscribableChannel
    @Input("myMessage")
    SubscribableChannel input();

    //此处的output就是对应于MessageChannel
    @Output("myMessage")
    MessageChannel output();

    @Input("myMessage2")
    SubscribableChannel input2();

    //此处的output就是对应于MessageChannel
    @Output("myMessage2")
    MessageChannel output2();
}
