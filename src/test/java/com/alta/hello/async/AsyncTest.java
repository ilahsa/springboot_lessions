package com.alta.hello.async;

import com.alta.hello.service.impl.MsgServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by baiba on 2018-11-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AsyncTest {

    @Autowired
    private MsgServer msgServer;

    @Test
    public void test() throws Exception {
        msgServer.sendA();
        msgServer.sendB();
    }
}