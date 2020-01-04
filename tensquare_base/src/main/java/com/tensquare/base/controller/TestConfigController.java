package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试读取码云上的自定义配置
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@RefreshScope
@RestController
@RequestMapping("/test")
public class TestConfigController {

    @Value("${sms.host}")
    private String sms_ip;

    @RequestMapping(value="/config",method = RequestMethod.GET)
    public Result findTestConfig(){
        return new Result(true, StatusCode.OK,"查询成功",sms_ip);
    }
}
