package com.alta.hello.controller;


import com.alibaba.fastjson.JSON;
import com.alta.hello.config.AltaConfig;
import com.alta.hello.config.MqProperties;
import com.alta.hello.context.ApplicationContextProvider;
import com.alta.hello.dao.impl.UserDao;
import com.alta.hello.domain.User;
import lombok.extern.slf4j.Slf4j;
import com.alta.hello.mqtt.MessageQueue;
import com.alta.hello.mqtt.MsgInfo;
import com.alta.hello.mqtt.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class HelloWorldController {
    @Autowired
    private  AltaConfig config;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MqProperties mqProperties;
    @Autowired
    protected Publisher publisher;

    @RequestMapping("/hello")
    public String index() {
        System.out.println(mqProperties.getClientId());

        return "Hello World1234";
    }

    @RequestMapping("/getuser")
    public User getUser(){
        User user = new User();
        user.setName(config.getTitle());
        user.setPassword("123456");
        log.info("getuser-----------");
        Map< String, Object> studentMap = jdbcTemplate.queryForMap("select * from user limit 1", new HashMap<>());
        log.info(studentMap.get("name")+"");
        List<User> users =userDao.all();
        log.info(users.stream().findFirst().get().getName());
        return  user;
    }

    //全局异常信息的测试
    @RequestMapping("/zeroex")
    public int exceptionTest(){
        return 100/0;
    }

    //测试ApplicationContext 容器
    @RequestMapping("/testDemo")
    public Object testDemo(){
        return ApplicationContextProvider.getBean("testDemo");
    }

    //试玩demo
    @RequestMapping("/playex")
    public Object playEx(){
        String topic = "remote/exec/alloc2";
        String msg = "{\"msg_id\":\"161906057277493248\",\"user_id\":\"1521830212107\",\"game_id\":\"com.noodlecake.altosadventure\",\"activity_name\":\"\",\"service_type\":\"webcocket\",\"connect_timeout\":15,\"reconnect_timeout\":10,\"access_key\":\"161906057277493248\",\"trial_timeout\":300,\"timestamp\":1521830212,\"orientation\":\"landscape\",\"tp_id\":\"\",\"expect_vm_id\":\"\",\"vm_config\":\"{\\n  \\\"videoResolutionH\\\": 1280,\\n  \\\"videoResolutionW\\\": 720,\\n  \\\"videoMaxBitrate\\\": 4000,\\n  \\\"videoCodec\\\": \\\"H264 Baseline\\\",\\n  \\\"isHwCodec\\\": true,\\n  \\\"dcOrder\\\": false,\\n  \\\"dcMaxAttempts\\\": 3,\\n  \\\"dcMaxDelay\\\": -1,\\n  \\\"videoMaxFps\\\": 0,\\n  \\\"videoNACK\\\": 1,\\n  \\\"videoFEC\\\": 1,\\n  \\\"debug\\\": false\\n}\",\"screenshot\":\"disabled\",\"rawlog\":\"disabled\",\"debug\":\"open\",\"clear_data\":\"open\",\"rtcMinKbps\":2000,\"rtcMaxKbps\":8000,\"rtcFps\":25,\"network\":\"0\"}";
        System.out.println(msg);
        publisher.pubMessage(topic,msg);
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> contentMap = new HashMap<>();
        try {
            //{"timestamp":1538206074,"msg_id":"161906057277493248","user_id":"1521830212107","result_code":0,"result_msg":"success","vm_id":"000000000060","data_channel":"ws://abc","ctrl_channel":"ws://xxx"}
            MsgInfo receiveMsg = MessageQueue.getInstantce().get(1000*20);
            if(receiveMsg!=null){
                Map<String,Object> payload = JSON.parseObject(receiveMsg.getPayload());
                result.put("result_code",payload.get("result_code"));
                result.put("result_msg",payload.get("result_msg")+"");
                contentMap.put("data_channel",payload.get("data_channel")+"");
                contentMap.put("ctrl_channel",payload.get("ctrl_channel")+"");
                contentMap.put("socket_control","ws://url.com/control");
                contentMap.put("accessKey","230263681332543488");
                result.put("result",contentMap);
                return result;
            }else {
                result.put("result_code",20201);
                result.put("result_msg","failed");
                result.put("result",null);
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
            result.put("result_code",20500);
            result.put("result_msg","system error");
            result.put("result",null);
            return result;
        }
    }
}