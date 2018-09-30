package com.alta.hello.mqtt;

import com.alta.hello.config.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by baiba on 2018-09-29.
 * mqtt 客户端
 */
@Slf4j
public class Publisher {

    private MqttAsyncClient client;
    @Autowired
    private MqProperties mqProperties;

    private MqttAsyncClient initClient() throws MqttException {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = new MqttAsyncClient(mqProperties.getHost(), mqProperties.getServerName() + mqProperties.getClientId(), new MqttDefaultFilePersistence(mqProperties.getTmpDir()));
                }
            }
        }
        return client;
    }

    public void init() {
        // 1.设置mqtt连接属性
        // 2.实例化mqtt客户端
        try {
            client = initClient();
            client.setCallback(new PubliserCallback());
            // 3.连接
            if(!client.isConnected()) {
                client.connect(mqProperties.getOptions());
                client.setBufferOpts(mqProperties.getBufferOptions());
            }
        } catch (MqttException e) {
            log.error("Publisher connect error: {}", e.getMessage());
        }
    }

    /**
     *
     * @Param:
     * @return:
     * @Description:  发布消息
     */
    public boolean pubMessage(String topic, String msg) {
        log.info("pubMessage -> topic: {}, payload: {}", topic, msg);
        if (this.client.isConnected()) {
            MqttMessage reqMessage = new MqttMessage(msg.getBytes());
            reqMessage.setQos(mqProperties.getQos());
            reqMessage.setRetained(mqProperties.isRetained());
            try {
                client.publish(topic, reqMessage);
            } catch (MqttException e) {
                log.error("msg publish error: {}", e.getMessage());
            }
            return true;
        }
        return false;
    }

    public void destroy() {
        if (null != client && client.isConnected()) {
            try {
                client.disconnect();
                log.debug("Publisher Client disconnect...");
            } catch (MqttException e) {
                log.error("Publisher Client disconnect error: {}", e.getMessage());
            }
        }
    }

    public MqttAsyncClient getClient() {
        return client;
    }
}