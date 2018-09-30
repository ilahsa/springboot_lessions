package com.alta.hello.mqtt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by baiba on 2018-09-29.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgInfo {
    private String topic;
    private String payload;
}

