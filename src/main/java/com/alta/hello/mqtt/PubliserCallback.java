package com.alta.hello.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by baiba on 2018-09-29.
 */
@Slf4j
public class PubliserCallback implements MqttCallbackExtended {

    @Override
    public void connectComplete(boolean b, String s) {
        log.info("connectComplete -> {}, {}", b , s);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.info("connectionLost -> {}", throwable);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        log.info("messageArrived -> topic: {}  info: {}", s, mqttMessage.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete -> {}", token.isComplete());
    }
}