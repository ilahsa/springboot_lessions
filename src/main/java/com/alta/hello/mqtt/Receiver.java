package com.alta.hello.mqtt;

import com.alta.hello.config.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by baiba on 2018-09-29.
 */
@Slf4j
public class Receiver {

    @Autowired
    private MqProperties mqProperties;

    private ReceiveCallBack receiverCallBack;

    private IMqttClient client;

    public Receiver(ReceiveCallBack _receiverCallBack) {
        receiverCallBack = _receiverCallBack;
    }

    private IMqttClient initClient() throws MqttException {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = new MqttClient(mqProperties.getHost(),
                            mqProperties.getServerName() + "_" + mqProperties.getClientId(), new MqttDefaultFilePersistence(mqProperties.getTmpDir()));
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
            client.setCallback(receiverCallBack);
            receiverCallBack.setClient(client);
            // 3.连接
            if(!client.isConnected()) {
                client.connect(mqProperties.getOptions());
                try {
                    client.subscribe("remote/result/alloc2/+", 2);
                } catch (MqttException e) {
                    log.error(e.getMessage(), e);
                }

            }
        } catch (MqttException e) {
            log.error("mqtt connect error: {}", e.getMessage());
        }
    }

    public void destroy() {
        if (null != client && client.isConnected()) {
            try {
                client.disconnect();
                client.disconnectForcibly();
                log.debug("MQTT Client disconnect...");
            } catch (MqttException e) {
                log.error("MQTT Client disconnect error: {}", e.getMessage());
            }
        }
    }
}