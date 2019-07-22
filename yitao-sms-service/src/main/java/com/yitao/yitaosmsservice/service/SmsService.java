package com.yitao.yitaosmsservice.service;

/**
 * @ClassName: SmsService
 * @Auther: admin
 * @Date: 2019/7/22 15:32
 * @Description:
 */
public interface SmsService {

    /**
     * 发送短信
     * @param phone 手机号码
     * @param code 验证码
     */
    void sendSms(String phone, String code);

}
