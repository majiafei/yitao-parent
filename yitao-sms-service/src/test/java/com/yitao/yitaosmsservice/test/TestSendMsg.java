package com.yitao.yitaosmsservice.test;

import com.yitao.yitaosmsservice.YitaoSmsServiceApplicationTests;
import com.yitao.yitaosmsservice.service.SmsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: TestSendMsg
 * @Auther: admin
 * @Date: 2019/7/22 15:46
 * @Description:
 */
public class TestSendMsg extends YitaoSmsServiceApplicationTests {

    @Autowired
    private SmsService smsService;

    @Test
    public void testSemdSms() {
        smsService.sendSms("13963949859", "123456");
    }

}
