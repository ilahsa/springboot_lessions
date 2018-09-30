package com.alta.hello.config;

import com.alta.hello.mqtt.Publisher;
import com.alta.hello.mqtt.ReceiveCallBack;
import com.alta.hello.mqtt.Receiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

/**
 * Created by baiba on 2018-09-29.
 */
@Configuration
@Order(2)
public class MqttConfig {
    @Bean
    @Scope("prototype")
    public ReceiveCallBack receiveCallBack() {
        ReceiveCallBack receiveCallBack = new ReceiveCallBack();
        return receiveCallBack;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Publisher publisher() {
        return new Publisher();
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Receiver receiver(ReceiveCallBack receiveCallBack) {
        Receiver receiver = new Receiver(receiveCallBack);
        return receiver;
    }

}
