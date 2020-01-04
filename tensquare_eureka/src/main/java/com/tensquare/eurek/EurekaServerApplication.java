package com.tensquare.eurek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka的启动类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}
