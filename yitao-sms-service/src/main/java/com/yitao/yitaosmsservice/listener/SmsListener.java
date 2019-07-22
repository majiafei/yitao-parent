package com.yitao.yitaosmsservice.listener;

import com.yitao.yitaosmsservice.service.SmsService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @ClassName: SmsListener
 * @Auther: admin
 * @Date: 2019/7/22 15:53
 * @Description:
 */
@Configuration
public class SmsListener {

    @Autowired
    private SmsService smsService;

    /**
     * 监听发送短信
     * @param msg 生产者发送过来的信息
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "yt.sms.queue",durable = "true"),
            exchange = @Exchange(value = "yt.sms.exchange", ignoreDeclarationExceptions = "true"), key = {"sms.verify.code"}))
    public void listenerSms(Map<String, String> msg) {
        if (CollectionUtils.isEmpty(msg)) {
            return;
        }

        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.hasText(phone) && StringUtils.hasText(code)) {
            try {
                smsService.sendSms(phone, code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
