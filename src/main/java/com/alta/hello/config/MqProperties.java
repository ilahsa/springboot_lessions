package com.alta.hello.config;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by baiba on 2018-09-29.
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Data
@Order(1)
public class MqProperties {
    /**
     * qos模式 : 0 只确保发送一次.无论接收是否成功, 1 确保发送成功 可能重复接收, 2 确保发送成功 有且只有一次
     */
    private String serverName;
    private String host;
    private String clientId;
    private int qos;
    private String tmpDir;
    private boolean retained;
    private MqttConnectOptions options;
    private DisconnectedBufferOptions bufferOptions;
}