package com.yitao.yitaosmsservice.service.impl;

import com.yitao.yitaosmsservice.service.SmsService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName: SmsSerivceImpl
 * @Auther: admin
 * @Date: 2019/7/22 15:32
 * @Description:
 */
@Service
public class SmsSerivceImpl implements SmsService {

    @Value("${yt.sms.url}")
    private String url;

    @Value("${yt.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${yt.sms.accessKeySecret}")
    private String accessKeySecret;

    @Override
    public void sendSms(String phone, String code) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        String content = new String("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", accessKeyId), //查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
                new NameValuePair("password", accessKeySecret), //查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            if("2".equals(code)){
                System.out.println("短信提交成功");
            }

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
