package com.tensquare.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * 搜索微服务的启动类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@SpringBootApplication
@EnableEurekaClient
public class SearchApplication {

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}

