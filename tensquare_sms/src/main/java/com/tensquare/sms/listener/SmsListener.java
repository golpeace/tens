package com.tensquare.sms.listener;

import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于消费消息
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Component
@RabbitListener(queues = "tensquare")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    /**
     * 处理消息的方法
     * @param map
     */
    @RabbitHandler
    public void sendsms(Map<String,String> map)throws Exception{
        String mobile = map.get("mobile");
        String code = map.get("code");

        System.out.println("手机号："+mobile+",验证码："+code);

        //调用工具类实现发送
        //{"code":"1"}
        smsUtil.sendSms(mobile,template_code,sign_name,"{\"code\":\""+code+"\"}");
    }
}
