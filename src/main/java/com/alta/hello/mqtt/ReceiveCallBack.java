package com.alta.hello.mqtt;

import com.alta.hello.config.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.midi.Soundbank;
import java.nio.charset.StandardCharsets;

/**
 * Created by baiba on 2018-09-29.
 * 消息回调
 */
@Slf4j
public class ReceiveCallBack implements MqttCallbackExtended {

    private IMqttClient client;

    @Autowired
    protected MqProperties mqProperties;

    @Override
    public void connectionLost(Throwable cause) {
        // 重建连接
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        if (topic.indexOf("\n") >= 0) {
            topic = topic.replaceAll("\n","");
        }

        System.out.println("receive:"+topic);
        System.out.println("receive:"+payload);
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setTopic(topic);
        msgInfo.setPayload(payload);
        MessageQueue.getInstantce().put(msgInfo);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete--------- {}", token.isComplete());
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.debug(reconnect + " --reconnect-- " + serverURI);
    }

    public void setClient(IMqttClient client) {
        this.client = client;
    }

    public IMqttClient getClient() {
        return client;
    }



}